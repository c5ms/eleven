package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.ImmutableValues;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.model.plan.event.PlanStarted;
import com.eleven.hotel.domain.model.plan.event.PlanStopped;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.*;

@Table(name = "hms_plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Plan extends AbstractEntity implements Saleable {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long planId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    private Map<ProductId, Product> products;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "sale_period_end"))
    private DateTimeRange salePeriod;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "pre_sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "pre_sale_period_end"))
    private DateTimeRange preSalePeriod;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "stay_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "stay_period_end"))
    private DateRange stayPeriod;

    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_count"))
    private StockAmount stockAmount;

    /**
     * <p>
     * it will affect the privacy
     * </p>
     *
     * @see #isPrivate
     */
    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Embedded
    private PlanBasic basic;

    protected Plan() {
        this.products = new HashMap<>();
        this.saleChannels = new HashSet<>();
        this.saleType = SaleType.NORMAL;
    }

    @PostPersist
    protected void onPostPersist() {
        for (Product product : this.getProducts()) {
            product.getProductId().setPlanId(this.getPlanId());

            for (Price price : product.getPrices()) {
                price.getPriceId().setPlanId(this.getPlanId());
            }
        }
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "normalBuilder", builderMethodName = "normal", buildMethodName = "create")
    public static Plan createNormal(Long hotelId,
                                    StockAmount stockAmount,
                                    DateRange stayPeriod,
                                    Set<SaleChannel> saleChannels,
                                    DateTimeRange salePeriod,
                                    DateTimeRange preSellPeriod,
                                    PlanBasic basic) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stockAmount, "stock must not be null");
        Validate.isTrue(stockAmount.greaterThanZero(), "total must gather than zero");

        var plan = new Plan();
        plan.setHotelId(hotelId);
        plan.setStockAmount(stockAmount);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setBasic(basic);

        Optional.ofNullable(saleChannels).ifPresent(channels -> channels.forEach(plan::openChannel));
        return plan;
    }

    public List<Inventory> createInventories() {
        Validate.notNull(this.planId, "the plan has not been created");

        List<Inventory> inventories = new ArrayList<>();

        for (Product product : getProducts()) {
            getStayPeriod()
                .dates()
                .map(localDate -> Inventory.of(product.getProductId(), localDate, product.getStockAmount()))
                .forEach(inventories::add);
        }

        return inventories;
    }


    @Override
    public void startSale() {
        Validate.isTrue(hasProduct(), "the plan has no room");
        Validate.isTrue(this.getProducts().stream().anyMatch(Product::hasPrice), "the plan has no price at all");
        Validate.isTrue(this.getProducts().stream().anyMatch(Product::hasStock), "the plan has no stock at all");

        this.setSaleState(SaleState.STARTED);
        this.addEvent(new PlanStarted(this));
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
        this.addEvent(new PlanStopped(this));
    }


    public void startSale(Long roomId) {
        var product = requireRoom(roomId);
        product.startSale();
        this.startSale();
    }

    public void stopSale(Long roomId) {
        var product = requireRoom(roomId);
        product.stopSale();

        if (!hasOnSaleProduct()) {
            this.stopSale();
        }
    }

    @Override
    public boolean isOnSale() {
        if (!this.saleState.isOnSale()) {
            return false;
        }
        if (this.salePeriod == null) {
            return false;
        }
        if (null != preSalePeriod && preSalePeriod.isCurrent()) {
            return true;
        }
        return salePeriod.isCurrent();
    }

    public Product addProduct(Long roomId, StockAmount stock) {
        var planRoom = new Product(this, roomId, stock);
        planRoom.setSaleChannels(this.saleChannels);
        this.products.put(planRoom.getProductId(), planRoom);
        return planRoom;
    }

    public void setPrice(Long roomId, SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        var product = requireRoom(roomId);
        product.setPrice(saleChannel, wholeRoomPrice);
    }

    public void setPrice(Long roomId,
                         SaleChannel saleChannel,
                         BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        var product = requireRoom(roomId);
        product.setPrice(saleChannel, onePersonPrice, twoPersonPrice, threePersonPrice, fourPersonPrice, fivePersonPrice);
    }


    public Optional<Product> findRoom(Long roomId) {
        var id = ProductId.of(hotelId, planId, roomId);
        return Optional.ofNullable(this.products.get(id));
    }

    public Product requireRoom(Long roomId) {
        return this.findRoom(roomId).orElseThrow(() -> new IllegalArgumentException("the room does not exist"));
    }

    public BigDecimal charge(Long roomId, SaleChannel saleChannel, int personCount) {
        return this.requireRoom(roomId)
            .findPrice(saleChannel)
            .orElseThrow(() -> new IllegalArgumentException("the room with such channel not exist"))
            .charge(personCount);
    }

    public boolean hasProduct() {
        return MapUtils.isNotEmpty(this.products);
    }

    public boolean hasProduct(Long roomID) {
        return this.products.containsKey(ProductId.of(hotelId, planId, roomID));
    }

    public boolean hasStock(Long roomID) {
        return this.requireRoom(roomID).hasStock();
    }

    public boolean isOnSale(Long roomId) {
        return this.findRoom(roomId)
            .map(Product::isOnSale)
            .orElse(false);
    }

    public boolean hasOnSaleProduct() {
        return this.getProducts().stream().anyMatch(Product::isOnSale);
    }

    public void openChannel(SaleChannel saleChannel) {
        this.saleChannels.add(saleChannel);
        this.getProducts().forEach(product -> product.openChannel(saleChannel));
        this.isPrivate = this.saleChannels.isEmpty();
    }

    public void closChannel(SaleChannel saleChannel, boolean dropPrices) {
        this.saleChannels.remove(saleChannel);

        for (Product product : this.getProducts()) {
            product.closeChannel(saleChannel, dropPrices);
        }

        this.isPrivate = this.saleChannels.isEmpty();
    }

    @Nonnull
    public DateRange getStayPeriod() {
        return Optional.ofNullable(stayPeriod).orElseGet(DateRange::empty);
    }

    @Nonnull
    public DateTimeRange getPreSalePeriod() {
        return Optional.ofNullable(preSalePeriod).orElseGet(DateTimeRange::empty);
    }

    @Nonnull
    public DateTimeRange getSalePeriod() {
        return Optional.ofNullable(salePeriod).orElseGet(DateTimeRange::empty);
    }

    @Nonnull
    public PlanBasic getBasic() {
        return Optional.ofNullable(basic).orElseGet(PlanBasic::new);
    }

    @Nonnull
    public ImmutableValues<Product> getProducts() {
        return ImmutableValues.of(products.values());
    }

    @Nonnull
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }
}

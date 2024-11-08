package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.utils.ImmutableValues;
import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.ObjectMatcher;
import com.eleven.hotel.domain.values.StockAmount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Table(name = "hms_plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Plan extends AbstractEntity  {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long planId;

    @Column(name = "hotel_id")
    private Long hotelId;

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

    @Setter
    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "sale_period_end"))
    private DateTimeRange salePeriod;

    @Setter
    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "pre_sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "pre_sale_period_end"))
    private DateTimeRange preSalePeriod;

    @Setter
    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "stay_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "stay_period_end"))
    private DateRange stayPeriod;

    @Setter
    @Embedded
    @AttributeOverride(name = "count", column = @Column(name = "stock_count"))
    private StockAmount stock;

    @Setter
    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Setter
    @Embedded
    private PlanBasic basic;

    protected Plan() {
        this.products = new HashMap<>();
        this.saleChannels = new HashSet<>();
        this.saleType = SaleType.NORMAL;
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
        Validate.notNull(stayPeriod, "sale period must not be null");
        Validate.notNull(stockAmount, "stock must not be null");
        Validate.isTrue(stockAmount.greaterThanZero(), "total must gather than zero");

        var plan = new Plan();
        plan.setHotelId(hotelId);
        plan.setStock(stockAmount);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setBasic(basic);

        Optional.ofNullable(saleChannels).ifPresent(channels -> channels.forEach(plan::openChannel));
        return plan;
    }

    @PostPersist
    protected void onAfterPersist() {

        for (Product product : this.getProducts()) {
            product.getProductId().setPlanId(this.getPlanId());

            for (Price price : product.getPrices()) {
                price.getPriceId().setPlanId(this.getPlanId());
            }
        }
    }

    public void startSale() {
        Validate.isTrue(this.getProducts().isNotEmpty(), "the plan has no room");
        Validate.isTrue(this.getProducts().stream().anyMatch(Product::hasPrice), "the plan has no price at all");
        Validate.isTrue(this.getProducts().stream().anyMatch(Product::hasStock), "the plan has no stock at all");

        this.setSaleState(SaleState.STARTED);
    }

    public void stopSale() {
        this.saleState = SaleState.STOPPED;
    }

    public boolean isOnSale() {
        if (!this.getSaleState().isOnSale()) {
            return false;
        }
        if (null != getPreSalePeriod() && getPreSalePeriod().isCurrent()) {
            return true;
        }
        return getSalePeriod().isCurrent();
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

    public Product addRoom(Long roomId, StockAmount stock) {
        var planRoom = new Product(this, roomId, stock);
        planRoom.setSaleChannels(this.saleChannels);
        this.products.put(planRoom.getProductId(), planRoom);
        return planRoom;
    }

    public Optional<Product> findRoom(Long roomId) {
        var id = ProductId.of(getHotelId(), getPlanId(), roomId);
        return Optional.ofNullable(this.products.get(id));
    }

    public Product requireRoom(Long roomId) {
        return this.findRoom(roomId).orElseThrow(() -> new IllegalArgumentException("the room does not exist"));
    }

    public BigDecimal chargeRoom(Long roomId, SaleChannel saleChannel, int personCount) {
        return this.requireRoom(roomId)
                .findPrice(saleChannel)
                .orElseThrow(() -> new IllegalArgumentException("the room with such channel not exist"))
                .charge(personCount);
    }

    public void startSale(Long roomId) {
        var product = requireRoom(roomId);
        product.startSale();
        this.startSale();
    }

    public void stopSale(Long roomId) {
        var product = requireRoom(roomId);
        product.stopSale();

        if (this.getProducts().stream().noneMatch(Product::isOnSale)) {
            this.stopSale();
        }
    }

    public void openChannel(SaleChannel saleChannel) {
        this.saleChannels.add(saleChannel);
        this.getProducts().forEach(product -> product.openChannel(saleChannel));
    }

    public void closChannel(SaleChannel saleChannel, boolean dropPrices) {
        this.saleChannels.remove(saleChannel);
        for (Product product : this.getProducts()) {
            product.closeChannel(saleChannel, dropPrices);
        }
    }

    public boolean hasRoom(Long roomId) {
        return this.products.containsKey(ProductId.of(getHotelId(), getPlanId(), roomId));
    }

    public boolean hasStock(Long roomId) {
        return this.requireRoom(roomId).hasStock();
    }

    public boolean hasPrice(Long roomId) {
        return this.requireRoom(roomId).hasPrice();
    }

    public List<Inventory> createInventories() {
        return getProducts()
                .stream()
                .flatMap(product -> createInventories(product).stream())
                .collect(Collectors.toList());
    }

    public boolean isApplicable(Inventory inventory) {
        ObjectMatcher<Inventory> matcher= new ObjectMatcher<Inventory>()
                .should(theInv -> theInv.getPlanKey().equals(toPlanKey()))
                .should(theInv -> this.getStayPeriod().contains(theInv.getInventoryKey().getDate()))
                .should(theInv -> this.hasRoom(theInv.getInventoryKey().getRoomId()))
                ;
        return matcher.test(inventory);

    }

    private ArrayList<Inventory> createInventories(Product product) {
        Validate.notNull(this.planId, "the plan has not been created");
        var inventories = new ArrayList<Inventory>();
        getStayPeriod()
                .dates()
                .map(localDate -> Inventory.of(product.getProductId(), localDate, product.getStockAmount()))
                .forEach(inventories::add);
        return inventories;
    }

    @Nonnull
    public DateRange getStayPeriod() {
        return Optional.ofNullable(stayPeriod).orElseGet(DateRange::empty);
    }

    @Nullable
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

    public PlanKey toPlanKey() {
        return PlanKey.of(hotelId, planId);
    }

    @Column(name = "is_private")
    private boolean isPrivate(){
        return getSaleChannels().isEmpty();
    }
}

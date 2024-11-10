package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainValidator;
import com.eleven.core.domain.utils.ImmutableValues;
import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.errors.PlanErrors;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.values.StockAmount;
import com.google.common.base.Predicates;
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
public class Plan extends AbstractEntity {

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
        plan.setSaleChannels(saleChannels);
        plan.setBasic(basic);
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
        DomainValidator.must(this.getProducts().isNotEmpty(), PlanErrors.NO_PRODUCT);
        for (Product product : this.getProducts()) {
            product.startSale();
        }
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
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        product.setPrice(saleChannel, wholeRoomPrice);
    }

    public void setPrice(Long roomId,
                         SaleChannel saleChannel,
                         BigDecimal onePersonPrice,
                         BigDecimal twoPersonPrice,
                         BigDecimal threePersonPrice,
                         BigDecimal fourPersonPrice,
                         BigDecimal fivePersonPrice) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        product.setPrice(saleChannel, onePersonPrice, twoPersonPrice, threePersonPrice, fourPersonPrice, fivePersonPrice);
    }

    public Product addRoom(Long roomId, StockAmount stock) {
        var productId = ProductId.of(getHotelId(), getPlanId(), roomId);
        var product = new Product(productId, this.saleType, this.saleChannels, stock);
        this.products.put(product.getProductId(), product);
        return product;
    }

    public Optional<Product> findRoom(Long roomId) {
        var id = ProductId.of(getHotelId(), getPlanId(), roomId);
        return Optional.ofNullable(this.products.get(id));
    }

    public BigDecimal chargeRoom(Long roomId, SaleChannel saleChannel, int personCount) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        var price = product.findPrice(saleChannel).orElseThrow(PlanErrors.PRICE_NOT_FOUND::toException);
        return price.charge(personCount);
    }

    public void startSale(Long roomId) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        product.startSale();
        this.startSale();
    }

    public void stopSale(Long roomId) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        product.stopSale();
        if (this.getProducts().stream().noneMatch(Product::isOnSale)) {
            this.stopSale();
        }
    }

    public List<Inventory> createInventories() {
        return getProducts()
            .stream()
            .flatMap(product -> createInventories(product).stream())
            .collect(Collectors.toList());
    }

    private List<Inventory> createInventories(Product product) {
        var creator = InventoryCreator.of(product);
        return getStayPeriod()
            .dates()
            .map(creator::create)
            .collect(Collectors.toList());
    }

    public boolean isApplicable(Inventory inventory) {
        return Predicates.<Inventory>notNull()
            .and(theInv -> theInv.getPlanKey().equals(toPlanKey()))
            .and(theInv -> this.getStayPeriod().contains(theInv.getInventoryKey().getDate()))
            .and(theInv -> this.findRoom(theInv.getInventoryKey().getRoomId()).isPresent())
            .test(inventory);
    }

    public Plan setSaleChannels(Set<SaleChannel> saleChannels) {
        this.saleChannels = saleChannels;
        for (Product product : this.getProducts()) {
            product.setSaleChannels(this.saleChannels);
        }
        return this;
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

    // todo why not a JPA field?
    @Column(name = "is_private")
    private Boolean getIsPrivate() {
        return getSaleChannels().isEmpty();
    }
}

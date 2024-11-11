package com.eleven.hotel.domain.model.plan;

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
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id", insertable = false, updatable = false)
    @JoinColumn(name = "plan_id", referencedColumnName = "plan_id", insertable = false, updatable = false)
    private List<Product> products;

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
        this.products = new ArrayList<>();
        this.saleChannels = new HashSet<>();
        this.saleType = SaleType.NORMAL;
    }

    @PrePersist
    protected void complete() {
        for (Product value : this.products) {
            value.getProductKey().set(getPlanKey());
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


    public boolean isOnSale() {
        if (!this.getSaleState().isOnSale()) {
            return false;
        }
        if (null != getPreSalePeriod() && getPreSalePeriod().isCurrent()) {
            return true;
        }
        return getSalePeriod().isCurrent();
    }

    public void removeRoom(Predicate<Product> predicate) {
        this.products.removeIf(predicate);
    }

    public void addRoom(@NonNull Long roomId) {
        var productKey = getProductKey(roomId);
        if (findRoom(roomId).isPresent()) {
            return;
        }
        var product = new Product(productKey, this.saleType, this.saleChannels, stock);
        this.products.add(product);
    }


    public Optional<Product> findRoom(Long roomId) {
        var productKey = getProductKey(roomId);
        return this.products.stream()
            .filter(product -> product.is(productKey))
            .findFirst();
    }

    public BigDecimal chargeRoom(Long roomId, SaleChannel saleChannel, int personCount) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        var price = product.findPrice(saleChannel).orElseThrow(PlanErrors.PRICE_NOT_FOUND::toException);
        return price.charge(personCount);
    }

    public void startSale(Long roomId) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        product.setSaleState(SaleState.STARTED);
        this.setSaleState(SaleState.STARTED);
    }

    public void stopSale(Long roomId) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);

        product.setSaleState(SaleState.STARTED);

        if (this.getProducts().stream().noneMatch(Product::isOnSale)) {
            this.setSaleState(SaleState.STOPPED);
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
            .and(theInv -> theInv.getPlanKey().equals(getPlanKey()))
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

    public Plan setStock(StockAmount stock) {
        this.stock = stock;
        for (Product value : this.products) {
            value.setStock(stock);
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
        return ImmutableValues.of(products);
    }

    @Nonnull
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }

    @Nonnull
    public PlanKey getPlanKey() {
        return PlanKey.of(hotelId, planId);
    }

    @Nonnull
    public ProductKey getProductKey(Long roomId) {
        return ProductKey.of(getPlanKey(), roomId);
    }
}

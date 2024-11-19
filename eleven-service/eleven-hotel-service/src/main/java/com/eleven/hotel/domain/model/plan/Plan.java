package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.error.DomainValidator;
import com.eleven.core.domain.values.ImmutableValues;
import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.domain.enums.SaleState;
import com.eleven.hotel.api.domain.enums.SaleType;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.errors.PlanErrors;
import com.eleven.hotel.domain.model.hotel.Room;
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
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Table(name = "plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Plan extends AbstractEntity {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private StockAmount stock;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Embedded
    private PlanBasic basic;

    protected Plan() {
        this.products = new ArrayList<>();
        this.saleChannels = new HashSet<>();
        this.saleType = SaleType.NORMAL;
        this.addEvent(PlanCreatedEvent.of(this));
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "Normal", builderMethodName = "normal", buildMethodName = "create")
    protected static Plan createNormal(Long hotelId,
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

    @PrePersist
    protected void beforePersist() {
        validate();

        for (Product value : this.products) {
            value.getKey().set(toKey());
        }
    }

    public void addRoom(@NonNull Room room) {
        var roomId = room.getRoomId();
        if (findRoom(roomId).isPresent()) {
            return;
        }
        var product = new Product(this, room, this.saleType, this.saleChannels, stock);
        this.products.add(product);
    }

    public void removeRoom(Predicate<Product> predicate) {
        this.products.removeIf(predicate);
    }

    public Optional<Product> findRoom(Long roomId) {
        var productKey = getProductKey(roomId);
        return this.products.stream()
                .filter(product -> product.is(productKey))
                .findFirst();
    }

    // todo no use
    public BigDecimal priceOf(Long roomId, SaleChannel saleChannel, int personCount) {
        var product = findRoom(roomId).orElseThrow(PlanErrors.PRODUCT_NOT_FOUND::toException);
        var price = product.priceOf(saleChannel).orElseThrow(PlanErrors.PRICE_NOT_FOUND::toException);
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

    public List<PlanInventory> createInventories() {
        return getProducts()
                .stream()
                .flatMap(product -> {
                    var creator = PlanInventoryFactory.of(product);
                    return getStayPeriod()
                            .dates()
                            .filter(localDate -> localDate.isAfter(LocalDate.now()))
                            .map(creator::create);
                })
                .collect(Collectors.toList());
    }

    public boolean isApplicable(PlanInventory planInventory) {
        return Predicates.<PlanInventory>notNull()
                .and(theInv -> theInv.getKey().toPlanKey().equals(toKey()))
                .and(theInv -> this.getStayPeriod().contains(theInv.getKey().getDate()))
                .and(theInv -> this.findRoom(theInv.getKey().getRoomId()).isPresent())
                .test(planInventory);
    }

    public void update(PlanPatch patch) {
        Optional.ofNullable(patch.getBasic()).ifPresent(this::setBasic);
        Optional.ofNullable(patch.getStock()).ifPresent(this::setStock);
        Optional.ofNullable(patch.getStayPeriod()).ifPresent(this::setStayPeriod);
        Optional.ofNullable(patch.getSalePeriod()).ifPresent(this::setSalePeriod);
        Optional.ofNullable(patch.getPreSalePeriod()).ifPresent(this::setPreSalePeriod);
        Optional.ofNullable(patch.getChannels()).ifPresent(this::setSaleChannels);
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
    public PlanKey toKey() {
        return PlanKey.of(hotelId, planId);
    }

    @Nonnull
    public ProductKey getProductKey(Long roomId) {
        return ProductKey.of(toKey(), roomId);
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

    private void setSaleChannels(Set<SaleChannel> saleChannels) {
        this.saleChannels = saleChannels;
        for (Product product : this.getProducts()) {
            product.setSaleChannels(this.saleChannels);
        }
    }

    private void setStock(StockAmount stock) {
        this.stock = stock;
        for (Product value : this.products) {
            value.setStock(stock);
        }
    }

    private void setStayPeriod(DateRange stayPeriod) {
        if (null != this.stayPeriod && !Objects.equals(this.stayPeriod, stayPeriod)) {
            this.addEvent(PlanStayPeriodChangedEvent.of(this));
        }
        this.stayPeriod = stayPeriod;
    }

    private void validate() {
        if (null != getPreSalePeriod() && getPreSalePeriod().isNotEmpty()) {
            DomainValidator.must(getPreSalePeriod().isBefore(getSalePeriod()), PlanErrors.PRE_SALE_PERIOD_ERROR);
        }
    }

}

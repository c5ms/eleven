package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.error.DomainValidator;
import com.eleven.core.domain.values.ImmutableValues;
import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.domain.enums.SaleState;
import com.eleven.hotel.api.domain.enums.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.errors.PlanErrors;
import com.eleven.hotel.domain.model.plan.event.PlanCreatedEvent;
import com.eleven.hotel.domain.model.plan.event.PlanStayPeriodChangedEvent;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
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

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @Column(name = "stock_count")
    private Integer stock;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels;

    @Embedded
    private PlanBasic basic;

    protected Plan() {
        this.saleChannels = new HashSet<>();
        this.saleType = SaleType.NORMAL;
        this.addEvent(PlanCreatedEvent.of(this));
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "Normal", builderMethodName = "normal", buildMethodName = "create")
    protected static Plan createNormal(Long hotelId,
                                       Integer stock,
                                       Set<SaleChannel> saleChannels,
                                       DateRange stayPeriod,
                                       DateTimeRange salePeriod,
                                       DateTimeRange preSellPeriod,
                                       PlanBasic basic) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stayPeriod, "sale period must not be null");
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock > 0, "stock must gather than zero");

        var plan = new Plan();
        plan.setHotelId(hotelId);
        plan.setStock(stock);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setSaleChannels(saleChannels);
        plan.setBasic(basic);
        plan.validate();
        return plan;
    }

    @PrePersist
    protected void beforePersist() {
        validate();
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
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }

    @Nonnull
    public PlanKey toKey() {
        return PlanKey.of(hotelId, planId);
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
    }

    private void setStock(Integer stock) {
        this.stock = stock;
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

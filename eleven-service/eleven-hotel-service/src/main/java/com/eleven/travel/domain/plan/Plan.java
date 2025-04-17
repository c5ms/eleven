package com.eleven.travel.domain.plan;

import com.eleven.framework.utils.ImmutableValues;
import com.eleven.travel.core.*;
import com.eleven.travel.core.support.DomainEntity;
import com.eleven.travel.domain.plan.event.PlanCreatedEvent;
import com.eleven.travel.domain.plan.event.PlanStayPeriodChangedEvent;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Table(name = "plan")
@Entity
@Getter
@Setter
@FieldNameConstants
public class Plan extends DomainEntity {

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
    private SaleState saleState = SaleState.STOPPED;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "sale_period_end"))
    private DateTimeRange salePeriod = DateTimeRange.empty();

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "pre_sale_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "pre_sale_period_end"))
    private DateTimeRange preSalePeriod = DateTimeRange.empty();

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "stay_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "stay_period_end"))
    private DateRange stayPeriod = DateRange.empty();

    @Column(name = "stock_count")
    private Integer stock;

    @Type(JsonType.class)
    @Column(name = "sale_channels", columnDefinition = "json")
    @Enumerated(EnumType.STRING)
    private Set<SaleChannel> saleChannels = new HashSet<>();

    @Embedded
    private PlanBasic basic = PlanBasic.empty();

    @SuppressWarnings("unused")
    @Builder(builderClassName = "Normal", builderMethodName = "normal", buildMethodName = "create")
    protected static Plan createNormal(Long hotelId,
                                       Integer stock,
                                       Set<SaleChannel> saleChannels,
                                       DateRange stayPeriod,
                                       DateTimeRange salePeriod,
                                       DateTimeRange preSellPeriod,
                                       PlanBasic basic) {
        var plan = new Plan();
        plan.setHotelId(hotelId);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setStock(stock);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(Optional.ofNullable(preSellPeriod).orElseGet(DateTimeRange::empty));
        plan.setBasic(Optional.ofNullable(basic).orElseGet(PlanBasic::empty));
        plan.setSaleChannels(saleChannels);
        plan.addEvent(PlanCreatedEvent.of(plan));
        return plan;
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
    public ImmutableValues<SaleChannel> getSaleChannels() {
        return ImmutableValues.of(saleChannels);
    }

    private void setSaleChannels(Set<SaleChannel> saleChannels) {
        Validate.notEmpty(saleChannels, "saleChannels can not be empty");
        this.saleChannels = saleChannels;
    }

    @Nonnull
    public PlanKey toKey() {
        return PlanKey.of(hotelId, planId);
    }

    public boolean isOnSale() {
        if (!this.getSaleState().isOnSale()) {
            return false;
        }
        return BooleanUtils.or(new boolean[]{
            getSalePeriod().isCurrent(),
            getPreSalePeriod().isCurrent(),
        });
    }

    public boolean isPreSale() {
        return !this.preSalePeriod.isEmpty();
    }

    private void setStock(Integer stock) {
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock > 0, "stock must gather than zero");
        this.stock = stock;
    }

    public void setSalePeriod(DateTimeRange salePeriod) {
        Validate.notNull(salePeriod, "salePeriod must not be null");
        Validate.isTrue(!salePeriod.isEmpty(), "salePeriod must not be empty");
        this.salePeriod = salePeriod;
    }

    private void setStayPeriod(DateRange stayPeriod) {
        Validate.notNull(stayPeriod, "stayPeriod must not be null");
        Validate.isTrue(!stayPeriod.isEmpty(), "stayPeriod must not be empty");

        if (!stayPeriod.equals(this.stayPeriod)) {
            this.addEvent(PlanStayPeriodChangedEvent.of(this));
            this.stayPeriod = stayPeriod;
        }
    }

    public void setHotelId(Long hotelId) {
        Validate.notNull(hotelId, "hotelId must not be null");
        this.hotelId = hotelId;
    }

    public void startSale() {
        this.setSaleState(SaleState.STARTED);
    }

    public void stopSale() {
        this.setSaleState(SaleState.STOPPED);
    }


}

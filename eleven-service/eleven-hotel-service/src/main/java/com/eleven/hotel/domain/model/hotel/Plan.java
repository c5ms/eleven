package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Table(name = "hms_plan")
@Entity
@Getter
@FieldNameConstants
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends AbstractEntity implements Saleable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    @Column(name = "plan_id")
    private Integer id;

    @NonNull
    @Column(name = "hotel_id")
    private Integer hotelId;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Set<PlanRoom> rooms = new HashSet<>();

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateTimeRange.Fields.start, column = @Column(name = "sale_period_start")),
            @AttributeOverride(name = DateTimeRange.Fields.end, column = @Column(name = "sale_period_end"))
    })
    private DateTimeRange salePeriod;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateTimeRange.Fields.start, column = @Column(name = "pre_sale_period_start")),
            @AttributeOverride(name = DateTimeRange.Fields.end, column = @Column(name = "pre_sale_period_end"))
    })
    private DateTimeRange preSalePeriod;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateRange.Fields.start, column = @Column(name = "stay_period_start")),
            @AttributeOverride(name =  DateRange.Fields.end, column = @Column(name = "stay_period_end"))
    })
    private DateRange stayPeriod;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = Stock.Fields.count, column = @Column(name = "stock_count")),
    })
    private Stock stock;

    @Nullable
    @Embedded
    private Description description;

    private Plan(@NonNull Integer hotelId) {
        this.hotelId = hotelId;
        this.rooms = new HashSet<>();
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "normalBuilder", builderMethodName = "normal", buildMethodName = "create")
    public static Plan createNormal(Integer hotelId,
                                    Stock stock,
                                    DateRange stayPeriod,
                                    DateTimeRange sellPeriod,
                                    DateTimeRange preSellPeriod,
                                    Description description) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock.greaterTan(Stock.ZERO), "total must gather than zero");

        var plan = new Plan(hotelId);
        plan.stock = stock;
        plan.salePeriod = sellPeriod;
        plan.stayPeriod = stayPeriod;
        plan.preSalePeriod = preSellPeriod;
        plan.saleType = SaleType.NORMAL;
        plan.saleState = SaleState.STOPPED;
        plan.description = description;
        return plan;
    }

    @Override
    public void startSale() {
        DomainHelper.must(hasRoom(), HotelErrors.PLAN_NO_ROOM);
        this.saleState = SaleState.STARTED;
    }

    @Override
    public void stopSale() {
        this.saleState = SaleState.STOPPED;
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

    public void preSale(DateTimeRange preSellPeriod) {
        DomainHelper.must(preSellPeriod.isBefore(this.salePeriod), HotelErrors.PLAN_PRE_SALE_NOT_BEFORE_SALE);

        this.preSalePeriod = preSellPeriod;
    }

    public void addRoom(Room room, Stock stock, Price price) {
        var planRoom = new PlanRoom(this, room, stock, price);
        this.rooms.add(planRoom);
    }


    public Optional<PlanRoom> findRoom(Integer roomId) {
        return this.rooms.stream()
                .filter(planRoom -> planRoom.getRoomId().equals(roomId))
                .findFirst();
    }

    public Optional<Price> chargeRoom(Room room) {
        return findRoom(room.getId()).map(PlanRoom::getPrice);
    }

    private boolean hasRoom() {
        return this.getRooms().isEmpty();
    }


    public DateRange getStayPeriod() {
        return Optional.ofNullable(stayPeriod).orElseGet(DateRange::new);
    }

    public DateTimeRange getPreSalePeriod() {
        return Optional.ofNullable(preSalePeriod).orElseGet(DateTimeRange::new);
    }

    public DateTimeRange getSalePeriod() {
        return Optional.ofNullable(salePeriod).orElseGet(DateTimeRange::new);
    }

    public Description getDescription() {
        return Optional.ofNullable(description).orElseGet(Description::new);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldNameConstants
    public static class Description {

        @Column(name = "plan_name")
        private String name;

        @Column(name = "plan_desc")
        private String desc;

    }
}

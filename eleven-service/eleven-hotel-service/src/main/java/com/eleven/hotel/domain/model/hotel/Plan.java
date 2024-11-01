package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.PriceType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.*;

@Table(name = "hms_plan")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Plan extends AbstractEntity implements Saleable {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Set<PlanRoom> rooms = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_type")
    private SaleType saleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_state")
    private SaleState saleState;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateTimeRange.Fields.start, column = @Column(name = "sale_period_start")),
            @AttributeOverride(name = DateTimeRange.Fields.end, column = @Column(name = "sale_period_end"))
    })
    private DateTimeRange salePeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateTimeRange.Fields.start, column = @Column(name = "pre_sale_period_start")),
            @AttributeOverride(name = DateTimeRange.Fields.end, column = @Column(name = "pre_sale_period_end"))
    })
    private DateTimeRange preSalePeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = DateRange.Fields.start, column = @Column(name = "stay_period_start")),
            @AttributeOverride(name = DateRange.Fields.end, column = @Column(name = "stay_period_end"))
    })
    private DateRange stayPeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = Stock.Fields.count, column = @Column(name = "stock_count")),
    })
    private Stock stock;

    @Embedded
    private PlanBasic basic;

    private Plan(@NonNull Integer hotelId) {
        this.hotelId = hotelId;
        this.rooms = new HashSet<>();
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "normalBuilder", builderMethodName = "normal", buildMethodName = "create")
    public static Plan createNormal(Integer hotelId,
                                    Stock stock,
                                    DateRange stayPeriod,
                                    DateTimeRange salePeriod,
                                    DateTimeRange preSellPeriod,
                                    PlanBasic basic) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock.greaterThanZero(), "total must gather than zero");

        var plan = new Plan(hotelId);
        plan.setStock(stock);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.basic = basic;
        return plan;
    }

    @Override
    public void startSale() {
        DomainContext.must(hasRoom(), HotelErrors.PLAN_NO_ROOM);
        this.setSaleState(SaleState.STARTED);
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
        DomainContext.must(preSellPeriod.isBefore(this.salePeriod), HotelErrors.PLAN_PRE_SALE_NOT_BEFORE_SALE);

        this.setPreSalePeriod(preSellPeriod);
    }

    public void addRoom(Room room, Stock stock) {
        var planRoom = new PlanRoom(this, room, stock);
        this.rooms.add(planRoom);
    }

    public void addRoom(Room room) {
        var planRoom = new PlanRoom(this, room, Stock.zero());
        this.rooms.add(planRoom);
    }

    public Optional<PlanRoom> findRoom(Integer roomId) {
        return this.rooms.stream()
                .filter(planRoom -> planRoom.getRoomId().equals(roomId))
                .findFirst();
    }

    public Optional<Price> chargeRoom(Room room) {
//        return findRoom(room.getId()).map(PlanRoom::getPrice);
        return null;
    }

    public void setPrice(Integer roomId, PriceType type, BigDecimal amount) {
        findRoom(roomId).ifPresent(planRoom -> planRoom.addPrice(type, amount));
    }

    private boolean hasRoom() {
        return this.getRooms().isEmpty();
    }

    @Nonnull
    public DateRange getStayPeriod() {
        return Optional.ofNullable(stayPeriod).orElseGet(DateRange::new);
    }

    @Nonnull
    public DateTimeRange getPreSalePeriod() {
        return Optional.ofNullable(preSalePeriod).orElseGet(DateTimeRange::new);
    }

    @Nonnull
    public DateTimeRange getSalePeriod() {
        return Optional.ofNullable(salePeriod).orElseGet(DateTimeRange::new);
    }

    @Nonnull
    public PlanBasic getBasic() {
        return Optional.ofNullable(basic).orElseGet(PlanBasic::new);
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldNameConstants
    public static class PlanBasic {

        @Column(name = "plan_name")
        private String name;

        @Column(name = "plan_desc")
        private String desc;

    }

    @Table(name = "hms_plan_room")
    @Entity
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldNameConstants
    public static class PlanRoom extends AbstractEntity {

        @Column(name = "hotel_id")
        private Integer hotelId;

        @Column(name = "plan_id", insertable = false, updatable = false)
        private Integer planId;

        @Column(name = "room_id")
        private Integer roomId;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = Stock.Fields.count, column = @Column(name = "stock_count")),
        })
        private Stock stock;

        @ElementCollection
        @MapKeyEnumerated(EnumType.STRING)
        @MapKeyColumn(name = "price_type")
        @CollectionTable(name = "hms_plan_price", joinColumns = {
                @JoinColumn(name = "plan_room_id", referencedColumnName = "id", nullable = false),
        })
        @AttributeOverrides({
                @AttributeOverride(name = "value.amount", column = @Column(name = "price_amount")),
        })
        private Map<PriceType, PlanPrice> prices = new HashMap<>();

        public PlanRoom(Plan plan, Room room, Stock stock) {
            this.planId = plan.getId();
            this.hotelId = plan.getHotelId();
            this.roomId = room.getId();
            this.stock = stock;
        }

        public void addPrice(PriceType type, BigDecimal amount) {
            this.prices.put(type, new PlanPrice(amount));
        }
    }

    @Embeddable
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldNameConstants
    public static class PlanPrice {

        private BigDecimal amount;

        public PlanPrice(BigDecimal amount) {
            this.amount = amount;
        }
    }
}

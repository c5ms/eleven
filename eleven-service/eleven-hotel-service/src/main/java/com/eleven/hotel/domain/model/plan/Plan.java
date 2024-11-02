package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.domain.error.HotelErrors;
import com.eleven.hotel.api.domain.model.PriceType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.core.Saleable;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @JoinColumn(name = "plan_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Set<PlanRoom> rooms = new HashSet<>();

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
    @AttributeOverrides({@AttributeOverride(name = "count", column = @Column(name = "stock_count")),})
    private Stock stock;

    @Embedded
    private PlanBasic basic;

    Plan(@NonNull Integer hotelId) {
        this.hotelId = hotelId;
        this.rooms = new HashSet<>();
    }

    @PostPersist
    void afterSave() {
        for (PlanRoom room : this.getRooms()) {
            room.setPlanId(this.getId());
        }
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

    public void addRoom(Room room, Stock stock) {
        var planRoom = new PlanRoom(this, room, stock);
        this.rooms.add(planRoom);
    }

    public void addRoom(Room room) {
        var planRoom = new PlanRoom(this, room, Stock.zero());
        this.rooms.add(planRoom);
    }

    public Optional<PlanPrice> findPrice(int roomId, PriceType type) {
        return this.findRoom(roomId)
            .flatMap(planRoom -> planRoom.findPrice(type));
    }

    public List<PlanPrice> findPrice(int roomId) {
        return this.rooms.stream()
            .filter(planRoom -> planRoom.getId().getRoomId().equals(roomId))
            .flatMap(planRoom -> planRoom.getPrices().stream())
            .toList();
    }

    public Optional<PlanRoom> findRoom(Integer roomId) {
        return this.rooms.stream()
            .filter(planRoom -> planRoom.getId().getRoomId().equals(roomId))
            .findFirst();
    }

    public Optional<Price> charge(int roomId, PriceType type, int nights) {
        return findRoom(roomId).flatMap(planRoom -> planRoom.charge(type, nights));
    }

    public void setPrice(Integer roomId, PriceType type, BigDecimal amount) {
        var room = findRoom(roomId).orElseThrow(() -> new IllegalArgumentException("no such room in the plan"));
        room.setPrice(type, amount);
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

}

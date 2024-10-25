package com.eleven.hotel.domain.model.plan;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Table(name = "plan")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}), access = AccessLevel.PROTECTED)
public class Plan extends AbstractEntity implements HotelAware, Sellable {

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private final String hotelId;

    @Column(value = "name")
    private String name;

    @Column(value = "sale_type")
    private SaleType saleType;

    @Column(value = "sell_state")
    private SaleState saleState;

    @Embedded.Empty(prefix = "sell_")
    private DateTimeRange salePeriod;

    @Embedded.Empty(prefix = "pre_sell_")
    private DateTimeRange preSalePeriod;

    @Embedded.Empty(prefix = "stay_")
    private DateRange stayPeriod;

    @MappedCollection(idColumn = "plan_id")
    private final Set<PlanRoom> rooms;

    @Embedded.Empty
    private Stock stock;

    @Embedded.Empty
    private PlanDesc desc;

    private Plan(String id, String hotelId) {
        this.id = id;
        this.hotelId = hotelId;
        this.rooms = new HashSet<>();
    }

    public static Plan createPlan(String id,
                                  Hotel hotel,
                                  String name,
                                  Stock stock,
                                  DateTimeRange sellPeriod,
                                  DateRange stayPeriod,
                                  PlanDesc desc) {

        DomainUtils.must(stock.greaterTan(Stock.ZERO), () -> new IllegalArgumentException("total must gather than zero"));

        var plan = new Plan(id, hotel.getId());
        plan.name = name;
        plan.desc = desc;
        plan.stock = stock;
        plan.salePeriod = sellPeriod;
        plan.stayPeriod = stayPeriod;
        plan.saleType = SaleType.NORMAL;
        plan.saleState = SaleState.STOPPED;
        return plan;
    }

    public void addRoom(Room room, Stock stock, Price price) {
        var planRoom = PlanRoom.create(this, room, stock, price);
        this.rooms.add(planRoom);
    }

    @Override
    public void startSale() {
        DomainUtils.must(hasRoom(), HotelErrors.PLAN_NO_ROOM);
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
        if (null != preSalePeriod && preSalePeriod.isEffective()) {
            return true;
        }
        return stayPeriod.isCurrent();
    }

    public void preSale(DateTimeRange preSellPeriod) {
        DomainUtils.must(preSellPeriod.isBefore(this.salePeriod), HotelErrors.PLAN_PRE_SALE_NOT_BEFORE_SALE);

        this.preSalePeriod = preSellPeriod;
    }

    public Optional<PlanRoom> findRoom(String roomId) {
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

}

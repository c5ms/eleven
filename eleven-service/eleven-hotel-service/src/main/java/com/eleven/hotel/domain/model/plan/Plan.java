package com.eleven.hotel.domain.model.plan;

import com.eleven.core.data.AbstractEntity;
import com.eleven.core.data.Audition;
import com.eleven.core.domain.DomainHelper;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.core.Sellable;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.eleven.hotel.domain.model.plan.Plan.TABLE_NAME;

@Table(name = TABLE_NAME)
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}), access = AccessLevel.PROTECTED)
public class Plan extends AbstractEntity implements Sellable {
    public static final String TABLE_NAME = "plan";
    public static final String DOMAIN_NAME = "Plan";

    @Id
    private final String id;

    @Column(value = "hotel_id")
    private final String hotelId;

    @Column(value = "plan_id")
    private final String planId;

    @MappedCollection(idColumn = "plan_id")
    private final Set<PlanRoom> rooms;

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

    @Embedded.Empty(prefix = "stock_")
    private Stock stock;

    @Embedded.Empty(prefix = "plan_")
    private Description description;

    @Embedded.Empty
    private Audition audition = Audition.empty();

    private Plan(String hotelId, String planId) {
        this.id = DomainHelper.nextId();
        this.hotelId = hotelId;
        this.planId = planId;
        this.rooms = new HashSet<>();
    }

    @SuppressWarnings("unused")
    @Builder(builderClassName = "normalBuilder", builderMethodName = "normal", buildMethodName = "create",access = AccessLevel.PROTECTED)
     static Plan createNormal(String hotelId,
                                    String planId,
                                    Stock stock,
                                    DateRange stayPeriod,
                                    DateTimeRange sellPeriod,
                                    DateTimeRange preSellPeriod,
                                    Description description) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock.greaterTan(Stock.ZERO), "total must gather than zero");

        var plan = new Plan(hotelId,planId);
        plan.stock = stock;
        plan.salePeriod = sellPeriod;
        plan.stayPeriod = stayPeriod;
        plan.preSalePeriod = preSellPeriod;
        plan.saleType = SaleType.NORMAL;
        plan.saleState = SaleState.STOPPED;
        plan.description = description;
        return plan;
    }

    public void addRoom(Room room, Stock stock, Price price) {
        var planRoom = PlanRoom.create(this, room, stock, price);
        this.rooms.add(planRoom);
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
        return stayPeriod.isCurrent();
    }

    public void preSale(DateTimeRange preSellPeriod) {
        DomainHelper.must(preSellPeriod.isBefore(this.salePeriod), HotelErrors.PLAN_PRE_SALE_NOT_BEFORE_SALE);

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

    @Getter
    @AllArgsConstructor
    @FieldNameConstants
    public static class Description {

        @Column(value = "name")
        private String name;

        @Column(value = "desc")
        private String desc;

    }
}

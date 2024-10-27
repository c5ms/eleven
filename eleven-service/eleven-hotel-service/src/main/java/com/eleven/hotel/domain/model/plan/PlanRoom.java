package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Slf4j
@Table(name = "plan_room")
@Getter
@FieldNameConstants
@AllArgsConstructor(onConstructor = @__({@PersistenceCreator}))
public class PlanRoom {

    @Column("hotel_id")
    private final String hotelId;

    @Column("plan_id")
    private final String planId;

    @Column("room_id")
    private final String roomId;

    @Embedded.Empty
    private Stock stock;

    @Embedded.Empty
    private Price price;

    private PlanRoom(Plan plan, Room room) {
        this.planId = plan.getId();
        this.hotelId = plan.getHotelId();
        this.roomId = room.getId();
    }

    static PlanRoom create(Plan plan, Room room, Stock stock, Price price) {
        if (stock.isZero()) {
            log.info("current stock is zero, the room can not be on sale");
        }

        if (price.isZero()) {
            log.info("current price is zero, the room can not be on sale");
        }

        var planRoom = new PlanRoom(plan, room);
        planRoom.stock = stock;
        planRoom.price = price;
        return planRoom;
    }

}

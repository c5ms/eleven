package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Table(name = "hms_hotel_room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
public class Room extends AbstractEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = GENERATOR_NAME)
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Setter
    @Embedded
    private RoomBasic basic;

    @Setter
    @Embedded
    private RoomRestriction restriction;

    @Column(name = "stock_count")
    private Integer count;

    @Embedded
    @AttributeOverride(name = "start", column = @Column(name = "stay_period_start"))
    @AttributeOverride(name = "end", column = @Column(name = "stay_period_end"))
    private DateRange stayPeriod;

    @Version
    @Column(name = "update_version")
    private Long version;

    protected Room() {

    }

    public static Room of(Long hotelId, RoomBasic basic, RoomRestriction restriction, DateRange stayPeriod, Integer count) {
        var room = new Room();
        room.setHotelId(hotelId);
        room.setBasic(basic);
        room.setRestriction(restriction);
        room.setCount(count);
        room.setStayPeriod(stayPeriod);
        return room;
    }

    public List<Inventory> createInventories() {
        if (null == stayPeriod) {
            return new ArrayList<>();
        }
        var inventoryBuilder = Inventory.builder()
                .room(this);
        return getStayPeriod()
                .dates()
                .filter(localDate -> localDate.isAfter(LocalDate.now()))
                .map(inventoryBuilder::date)
                .map(Inventory.InventoryBuilder::build)
                .collect(Collectors.toList());
    }


    @Nonnull
    public RoomKey toKey() {
        return RoomKey.of(hotelId, roomId);
    }
}

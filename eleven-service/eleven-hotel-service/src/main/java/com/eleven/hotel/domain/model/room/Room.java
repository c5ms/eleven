package com.eleven.hotel.domain.model.room;

import com.eleven.hotel.domain.core.AbstractEntity;
import com.eleven.hotel.domain.model.plan.PlanKey;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;


@Table(name = "hms_hotel_room")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Version
    @Column(name = "update_version")
    private Long version;

    protected Room(Long hotelId, RoomBasic basic, RoomRestriction restriction) {
        this.setHotelId(hotelId);
        this.setBasic(basic);
        this.setRestriction(restriction);
    }

    public static Room of(Long hotelId, RoomBasic basic, RoomRestriction restriction) {
        return new Room(hotelId, basic, restriction);
    }

    @Nonnull
    public RoomKey toKey() {
        return RoomKey.of(hotelId, roomId);
    }
}

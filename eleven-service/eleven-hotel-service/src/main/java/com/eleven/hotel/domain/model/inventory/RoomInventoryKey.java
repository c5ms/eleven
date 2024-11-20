package com.eleven.hotel.domain.model.inventory;

import com.eleven.hotel.domain.model.hotel.RoomKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInventoryKey implements Serializable {

    @NonNull
    @Column(name = "hotel_id")
    private Long hotelId;

    @NonNull
    @Column(name = "room_id")
    private Long roomId;

    @NonNull
    @Column(name = "sale_date")
    private LocalDate date;

    public RoomInventoryKey(RoomKey roomKey, LocalDate date) {
        this.setHotelId(roomKey.getHotelId());
        this.setRoomId(roomKey.getRoomId());
        this.setDate(date);
    }

    public static RoomInventoryKey of(RoomKey roomKey, LocalDate date) {
        return new RoomInventoryKey(roomKey, date);
    }

    public RoomKey toRoomKey() {
        return RoomKey.of(this.getHotelId(), this.getRoomId());
    }

}

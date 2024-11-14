package com.eleven.hotel.domain.model.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomKey {

    @Column(name = "hotel_id", updatable = false, insertable = false)
    private Long hotelId;

    @Column(name = "room_id", updatable = false, insertable = false)
    private Long roomId;

    public static RoomKey of(@NotNull Long hotelId, @NotNull Long roomId) {
        return new RoomKey(hotelId, roomId);
    }

}

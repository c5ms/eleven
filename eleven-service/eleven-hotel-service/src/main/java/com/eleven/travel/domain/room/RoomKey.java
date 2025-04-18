package com.eleven.travel.domain.room;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomKey implements Serializable {

    @Column(name = "hotel_id", updatable = false, insertable = false)
    private Long hotelId;

    @Column(name = "room_id", updatable = false, insertable = false)
    private Long roomId;

    public static RoomKey of(@NotNull Long hotelId, @NotNull Long roomId) {
        return new RoomKey(hotelId, roomId);
    }

}

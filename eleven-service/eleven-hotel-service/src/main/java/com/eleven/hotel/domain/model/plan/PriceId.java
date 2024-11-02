package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceId implements Serializable {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    public static PriceId from(PlanRoomId planRoomId) {
        PriceId priceId = new PriceId();
        priceId.hotelId = planRoomId.getHotelId();
        priceId.planId = planRoomId.getPlanId();
        priceId.roomId = planRoomId.getRoomId();
        return priceId;
    }
}

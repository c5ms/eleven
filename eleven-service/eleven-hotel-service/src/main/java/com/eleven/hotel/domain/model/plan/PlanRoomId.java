package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanRoomId implements Serializable {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

}

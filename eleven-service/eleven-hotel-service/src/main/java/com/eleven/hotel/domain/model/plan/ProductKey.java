package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductKey implements Serializable {

    @Column(name = "hotel_id", insertable = false, updatable = false)
    private Long hotelId;

    @Column(name = "plan_id", insertable = false, updatable = false)
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    public ProductKey(Long hotelId, Long planId, Long roomId) {
        this.hotelId = hotelId;
        this.planId = planId;
        this.roomId = roomId;
    }

    public static ProductKey of(Long hotelId, Long planId, Long roomId) {
        return new ProductKey(hotelId, planId, roomId);
    }
}

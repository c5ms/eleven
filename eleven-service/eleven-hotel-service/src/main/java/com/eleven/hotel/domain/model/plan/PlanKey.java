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
public class PlanKey implements Serializable {

    @Column(name = "hotel_id", updatable = false, insertable = false)
    private Long hotelId;

    @Column(name = "plan_id", updatable = false, insertable = false)
    private Long planId;


    public PlanKey(Long hotelId, Long planId) {
        this.setHotelId(hotelId);
        this.setPlanId(planId);
    }

    public static PlanKey of(Long hotelId, Long planId) {
        return new PlanKey(hotelId, planId);
    }

}

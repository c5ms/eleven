package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@MappedSuperclass
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanKey implements Serializable {

    @Column(name = "hotel_id", updatable = false, insertable = false)
    private Long hotelId;

    @Column(name = "plan_id", updatable = false, insertable = false)
    private Long planId;

    protected PlanKey(Long hotelId, Long planId) {
        this.setHotelId(hotelId);
        this.setPlanId(planId);
    }

    public static PlanKey of(Long hotelId, Long planId) {
        return new PlanKey(hotelId, planId);
    }

}

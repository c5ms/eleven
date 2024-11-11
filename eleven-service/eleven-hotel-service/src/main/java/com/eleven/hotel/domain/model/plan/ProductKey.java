package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductKey implements Serializable {

    @NonNull
    @Column(name = "hotel_id", insertable = false, updatable = false)
    private Long hotelId;

    @NonNull
    @Column(name = "plan_id", insertable = false, updatable = false)
    private Long planId;

    @NonNull
    @Column(name = "room_id")
    private Long roomId;

    public static ProductKey of(Long hotelId, Long planId, Long roomId) {
        return new ProductKey(hotelId, planId, roomId);
    }

    public PlanKey toPlanKey() {
        return PlanKey.of(this.hotelId, this.planId);
    }

}

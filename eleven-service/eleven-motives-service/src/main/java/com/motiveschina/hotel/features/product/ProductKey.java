package com.motiveschina.hotel.features.product;

import java.io.Serializable;
import com.motiveschina.hotel.features.plan.PlanKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductKey implements Serializable {

    @NotNull
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @NotNull
    @Column(name = "room_id")
    private Long roomId;

    public ProductKey(PlanKey planKey, Long roomId) {
        this.hotelId = planKey.getHotelId();
        this.planId = planKey.getPlanId();
        this.roomId = roomId;
    }

    public static ProductKey of(Long hotelId, Long planId, Long roomId) {
        return new ProductKey(hotelId, planId, roomId);
    }

    public static ProductKey of(PlanKey planKey, Long roomId) {
        return new ProductKey(planKey, roomId);
    }

    public void set(PlanKey planKey) {
        this.setHotelId(planKey.getHotelId());
        this.setPlanId(planKey.getPlanId());
    }

    public PlanKey toPlanKey() {
        return PlanKey.of(this.hotelId, this.planId);
    }

}

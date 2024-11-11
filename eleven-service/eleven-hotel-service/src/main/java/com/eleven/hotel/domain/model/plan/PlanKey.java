package com.eleven.hotel.domain.model.plan;

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
public class PlanKey implements Serializable {

    @NonNull
    @Column(name = "hotel_id", updatable = false, insertable = false)
    private Long hotelId;

    @NonNull
    @Column(name = "plan_id", updatable = false, insertable = false)
    private Long planId;

    public static PlanKey of(@NotNull Long hotelId, @NotNull Long planId) {
        return new PlanKey(hotelId, planId);
    }

}

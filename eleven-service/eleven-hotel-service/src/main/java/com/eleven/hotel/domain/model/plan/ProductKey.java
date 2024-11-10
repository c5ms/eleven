package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductKey extends PlanKey {

    @Column(name = "room_id", insertable = true, updatable = true)
    private Long roomId;

    public static ProductKey of(Long hotelId, Long planId, Long roomId) {
        ProductKey productKey = new ProductKey();
        productKey.hotelId = hotelId;
        productKey.planId = planId;
        productKey.roomId = roomId;
        return productKey;
    }
}

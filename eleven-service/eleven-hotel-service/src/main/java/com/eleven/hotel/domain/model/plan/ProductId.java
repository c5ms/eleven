package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductId implements Serializable {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    public static ProductId of(Integer hotelId, Integer planId, Integer roomId) {
        ProductId productId = new ProductId();
        productId.hotelId = hotelId;
        productId.planId = planId;
        productId.roomId = roomId;
        return  productId;
    }
}

package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceId implements Serializable {

    @Nonnull
    @Column(name = "hotel_id")
    private Integer hotelId;

    @Nonnull
    @Column(name = "plan_id")
    private Integer planId;

    @Nonnull
    @Column(name = "room_id")
    private Integer roomId;

    @Nonnull
    @Enumerated(EnumType.STRING)
    @Column(name = "sale_channel")
    private SaleChannel saleChannel;

    public static PriceId of(PlanRoomId planRoomId, SaleChannel saleChannel) {
        PriceId priceId = new PriceId();
        priceId.hotelId = planRoomId.getHotelId();
        priceId.planId = planRoomId.getPlanId();
        priceId.roomId = planRoomId.getRoomId();
        priceId.saleChannel = saleChannel;
        return priceId;
    }
}

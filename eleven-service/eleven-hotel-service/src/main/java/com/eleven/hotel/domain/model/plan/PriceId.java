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
    private Long hotelId;

    @Nonnull
    @Column(name = "plan_id")
    private Long planId;

    @Nonnull
    @Column(name = "room_id")
    private Long roomId;

    @Nonnull
    @Enumerated(EnumType.STRING)
    @Column(name = "sale_channel")
    private SaleChannel saleChannel;

    public static PriceId of(ProductKey productKey, SaleChannel saleChannel) {
        PriceId priceId = new PriceId();
        priceId.hotelId = productKey.getHotelId();
        priceId.planId = productKey.getPlanId();
        priceId.roomId = productKey.getRoomId();
        priceId.saleChannel = saleChannel;
        return priceId;
    }
}

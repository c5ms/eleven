package com.eleven.domain.product;

import com.eleven.common.domain.SaleChannel;
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
public class PriceKey implements Serializable {

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

    public static PriceKey of(ProductKey productKey, SaleChannel saleChannel) {
        var priceKey = new PriceKey();
        priceKey.hotelId = productKey.getHotelId();
        priceKey.planId = productKey.getPlanId();
        priceKey.roomId = productKey.getRoomId();
        priceKey.saleChannel = saleChannel;
        return priceKey;
    }

    public void apply(ProductKey productKey) {
        this.setHotelId(productKey.getHotelId());
        this.setPlanId(productKey.getPlanId());
        this.setRoomId(productKey.getRoomId());
    }
}

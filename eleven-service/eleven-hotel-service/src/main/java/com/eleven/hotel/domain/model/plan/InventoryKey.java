package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryKey implements Serializable {
    private static final long DIGEST_VERSION = 1L;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "sale_date")
    private LocalDate date;

    public InventoryKey(ProductId productId, LocalDate date) {
        this.setHotelId(productId.getHotelId());
        this.setPlanId(productId.getPlanId());
        this.setRoomId(productId.getRoomId());
        this.setDate(date);
    }

    public static InventoryKey of(ProductId productId, LocalDate date) {
        return new InventoryKey(productId,date);
    }

    public String digest() {
        return DIGEST_VERSION + "#" +
               planId + "#" +
               hotelId + "#" +
               roomId + "#" +
               date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }


}

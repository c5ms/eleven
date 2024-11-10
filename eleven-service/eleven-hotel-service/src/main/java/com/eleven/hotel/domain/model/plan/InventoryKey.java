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

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "sale_date")
    private LocalDate date;

    public InventoryKey(ProductKey productKey, LocalDate date) {
        this.setHotelId(productKey.getHotelId());
        this.setPlanId(productKey.getPlanId());
        this.setRoomId(productKey.getRoomId());
        this.setDate(date);
    }

    public static InventoryKey of(ProductKey productKey, LocalDate date) {
        return new InventoryKey(productKey,date);
    }

}

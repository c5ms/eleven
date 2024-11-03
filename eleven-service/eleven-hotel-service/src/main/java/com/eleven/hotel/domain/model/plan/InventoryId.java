package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryId implements Serializable {

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "sale_date")
    private LocalDate date;


    public static InventoryId of(ProductId productId, LocalDate date) {
        InventoryId id = new InventoryId();
        id.setHotelId(productId.getHotelId());
        id.setPlanId(productId.getPlanId());
        id.setRoomId(productId.getRoomId());
        id.setDate(date);
        return id;
    }

}

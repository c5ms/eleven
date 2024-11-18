package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.domain.model.hotel.RoomKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanInventoryKey implements Serializable {

    @NonNull
    @Column(name = "hotel_id")
    private Long hotelId;

    @NonNull
    @Column(name = "plan_id")
    private Long planId;

    @NonNull
    @Column(name = "room_id")
    private Long roomId;

    @NonNull
    @Column(name = "sale_date")
    private LocalDate date;

    public PlanInventoryKey(ProductKey productKey, LocalDate date) {
        this.setHotelId(productKey.getHotelId());
        this.setPlanId(productKey.getPlanId());
        this.setRoomId(productKey.getRoomId());
        this.setDate(date);
    }

    public PlanInventoryKey(PlanKey planKey, RoomKey roomKey, LocalDate date) {
        this.setHotelId(planKey.getHotelId());
        this.setPlanId(planKey.getPlanId());
        this.setRoomId(roomKey.getRoomId());
        this.setDate(date);
    }

    public static PlanInventoryKey of(ProductKey productKey, LocalDate date) {
        return new PlanInventoryKey(productKey, date);
    }

    public static PlanInventoryKey of(PlanKey planKey, RoomKey roomKey, LocalDate date) {
        return new PlanInventoryKey(planKey, roomKey, date);
    }

    public PlanKey toPlanKey() {
        return PlanKey.of(this.hotelId, this.planId);
    }

    public ProductKey toProductKey() {
        return ProductKey.of(toPlanKey(), this.roomId);
    }

}

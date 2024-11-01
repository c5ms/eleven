package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.PriceType;
import com.eleven.hotel.domain.values.Price;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
@Entity
@Table(name = "hms_plan_price")
public class PlanPrice {

    @EmbeddedId
    private Id id;

    @AttributeOverride(name = "amount", column = @Column(name = "price_amount"))
    private Price price;

      PlanPrice(PlanRoom.Id planRoomId, PriceType type, Price price) {
        this.id = new Id(planRoomId, type);
        this.price = price;
    }

    void setPlanId(Integer planId) {
        this.id.planId = planId;
    }

    boolean isType(PriceType type) {
        return this.id.type == type;
    }

    @Getter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    static class Id implements Serializable {

        @Column(name = "hotel_id")
        private Integer hotelId;

        @Column(name = "plan_id")
        private Integer planId;

        @Column(name = "room_id")
        private Integer roomId;

        @Column(name = "price_type")
        @Enumerated(EnumType.STRING)
        private PriceType type;

        Id(PlanRoom.Id planRoomId, PriceType type) {
            this.hotelId = planRoomId.getHotelId();
            this.planId = planRoomId.getPlanId();
            this.roomId = planRoomId.getRoomId();
            this.type = type;
        }
    }
}

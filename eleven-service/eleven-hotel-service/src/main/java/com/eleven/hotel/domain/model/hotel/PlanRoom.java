package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Table(name = "hms_plan_room")
@Entity
@Getter
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id",insertable = false, updatable = false)
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    @Embedded
    private Stock stock;

    @Embedded
    private Price price;

    public PlanRoom(Plan plan, Room room, Stock stock, Price price) {
        this.planId = plan.getId();
        this.hotelId = plan.getHotelId();
        this.roomId = room.getId();
        this.stock = stock;
        this.price = price;
    }

}

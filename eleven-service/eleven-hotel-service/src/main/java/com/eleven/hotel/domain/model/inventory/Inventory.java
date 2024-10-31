package com.eleven.hotel.domain.model.inventory;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.domain.core.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "hms_inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Inventory extends AbstractEntity {

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "sale_date")
    private LocalDate date;

    @Column(name = "charge_type")
    private ChargeType chargeType;

    @Embedded
    private ChargeFee chargeFee;

    @Embeddable
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @FieldNameConstants
    public static class ChargeFee {

        @Column(name = "charge_price_1")
        private BigDecimal one;

        @Column(name = "charge_price_2")
        private BigDecimal two;

        @Column(name = "charge_price_3")
        private BigDecimal three;

        @Column(name = "charge_price_4")
        private BigDecimal four;

        @Column(name = "charge_price_5")
        private BigDecimal five;

    }


}

package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;


@Entity
@Table(name = "hms_price")
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Price {

    @EmbeddedId
    private PriceId id;

    @Column(name = "price_type")
    @Enumerated(EnumType.STRING)
    private ChargeType priceType;

    @Column(name = "whole_room_price")
    private BigDecimal wholeRoomPrice;

    @Column(name = "one_person_price")
    private BigDecimal onePersonPrice;

    @Column(name = "two_person_price")
    private BigDecimal twoPersonPrice;

    @Column(name = "three_person_price")
    private BigDecimal threePersonPrice;

    @Column(name = "four_person_price")
    private BigDecimal fourPersonPrice;

    @Column(name = "five_person_price")
    private BigDecimal fivePersonPrice;

    public boolean is(SaleChannel saleChannel) {
        return id.getSaleChannel() == saleChannel;
    }

}

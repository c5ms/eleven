package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;


@Entity
@Table(name = "hms_plan_price")
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldNameConstants
public class Price {

    @EmbeddedId
    private PriceId priceId;

    @Column(name = "price_type")
    @Enumerated(EnumType.STRING)
    private ChargeType priceType;

    @Column(name = "whole_room_price")
    private BigDecimal wholeRoomPrice = BigDecimal.ZERO;

    @Column(name = "one_person_price")
    private BigDecimal onePersonPrice = BigDecimal.ZERO;

    @Column(name = "two_person_price")
    private BigDecimal twoPersonPrice = BigDecimal.ZERO;

    @Column(name = "three_person_price")
    private BigDecimal threePersonPrice = BigDecimal.ZERO;

    @Column(name = "four_person_price")
    private BigDecimal fourPersonPrice = BigDecimal.ZERO;

    @Column(name = "five_person_price")
    private BigDecimal fivePersonPrice = BigDecimal.ZERO;


    public static Price wholeRoom(ProductId productId, SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        Price price = new Price();
        price.setPriceId(PriceId.of(productId, saleChannel));
        price.setPriceType(ChargeType.BY_ROOM);
        price.setWholeRoomPrice(wholeRoomPrice);
        return price;
    }

    public static Price byPerson(ProductId productId,
                                 SaleChannel saleChannel,
                                 BigDecimal onePersonPrice,
                                 BigDecimal twoPersonPrice,
                                 BigDecimal threePersonPrice,
                                 BigDecimal fourPersonPrice,
                                 BigDecimal fivePersonPrice) {
        Price price = new Price();
        price.setPriceId(PriceId.of(productId, saleChannel));
        price.setPriceType(ChargeType.BY_PERSON);
        price.setOnePersonPrice(onePersonPrice);
        price.setTwoPersonPrice(twoPersonPrice);
        price.setThreePersonPrice(threePersonPrice);
        price.setFourPersonPrice(fourPersonPrice);
        price.setFivePersonPrice(fivePersonPrice);
        return price;
    }

    public boolean is(SaleChannel saleChannel) {
        return priceId.getSaleChannel() == saleChannel;
    }

}

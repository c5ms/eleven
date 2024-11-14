package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.Validate;

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
    private PriceKey key;

    @Column(name = "charge_type")
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

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

    public static Price wholeRoom(ProductKey productKey, SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        var price = new Price();
        price.setKey(PriceKey.of(productKey, saleChannel));
        price.setChargeType(ChargeType.BY_ROOM);
        price.setWholeRoomPrice(wholeRoomPrice);
        return price;
    }

    public static Price byPerson(ProductKey productKey,
                                 SaleChannel saleChannel,
                                 BigDecimal onePersonPrice,
                                 BigDecimal twoPersonPrice,
                                 BigDecimal threePersonPrice,
                                 BigDecimal fourPersonPrice,
                                 BigDecimal fivePersonPrice) {
        Price price = new Price();
        price.setKey(PriceKey.of(productKey, saleChannel));
        price.setChargeType(ChargeType.BY_PERSON);
        price.setOnePersonPrice(onePersonPrice);
        price.setTwoPersonPrice(twoPersonPrice);
        price.setThreePersonPrice(threePersonPrice);
        price.setFourPersonPrice(fourPersonPrice);
        price.setFivePersonPrice(fivePersonPrice);
        return price;
    }

    public boolean is(SaleChannel saleChannel) {
        return key.getSaleChannel() == saleChannel;
    }

    public BigDecimal charge(int personCount) {
        return switch (this.chargeType) {
            case BY_ROOM -> this.chargeByRoom();
            case BY_PERSON -> this.chargeByPerson(personCount);
        };
    }

    private BigDecimal chargeByPerson(int personCount) {
        Validate.isTrue(personCount > 0, "person count must gather than zero");

        if (1 == personCount) {
            return this.onePersonPrice;
        }
        if (2 == personCount) {
            return this.twoPersonPrice;
        }
        if (3 == personCount) {
            return this.twoPersonPrice;
        }
        if (4 == personCount) {
            return this.fourPersonPrice;
        }
        return this.fivePersonPrice;
    }

    private BigDecimal chargeByRoom() {
        return this.wholeRoomPrice;
    }
}

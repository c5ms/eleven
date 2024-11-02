package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PriceCreator {

    public static Price wholeRoom(PlanRoomId planRoomId, SaleChannel saleChannel, BigDecimal wholeRoomPrice) {
        Price price = new Price();
        price.setId(PriceId.of(planRoomId, saleChannel));
        price.setPriceType(ChargeType.BY_ROOM);
        price.setWholeRoomPrice(wholeRoomPrice);
        return price;
    }

    public static Price byPerson(PlanRoomId planRoomId,
                                 SaleChannel saleChannel,
                                 BigDecimal onePersonPrice,
                                 BigDecimal twoPersonPrice,
                                 BigDecimal threePersonPrice,
                                 BigDecimal fourPersonPrice,
                                 BigDecimal fivePersonPrice) {
        Price price = new Price();
        price.setId(PriceId.of(planRoomId, saleChannel));
        price.setPriceType(ChargeType.BY_PERSON);
        price.setOnePersonPrice(onePersonPrice);
        price.setTwoPersonPrice(twoPersonPrice);
        price.setThreePersonPrice(threePersonPrice);
        price.setFourPersonPrice(fourPersonPrice);
        price.setFivePersonPrice(fivePersonPrice);
        return price;
    }

}

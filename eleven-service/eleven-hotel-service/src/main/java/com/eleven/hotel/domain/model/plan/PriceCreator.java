package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@UtilityClass
public class PriceCreator {

    public static Price wholeRoom(PlanRoomId planRoomId, BigDecimal wholeRoomPrice) {
        Price price= new Price();
        price.setType(ChargeType.BY_ROOM);
        price.setId(PriceId.from(planRoomId));
        price.setWholeRoomPrice(wholeRoomPrice);
        return price;
    }

    public static Price byPerson(PlanRoomId planRoomId,
                                 BigDecimal onePersonPrice,
                                 BigDecimal twoPersonPrice,
                                 BigDecimal threePersonPrice,
                                 BigDecimal fourPersonPrice,
                                 BigDecimal fivePersonPrice) {
        Price price= new Price();
        price.setType(ChargeType.BY_PERSON);
        price.setId(PriceId.from(planRoomId));
        price.setOnePersonPrice(onePersonPrice);
        price.setTwoPersonPrice(twoPersonPrice);
        price.setThreePersonPrice(threePersonPrice);
        price.setFourPersonPrice(fourPersonPrice);
        price.setFivePersonPrice(fivePersonPrice);
        return price;
    }

}

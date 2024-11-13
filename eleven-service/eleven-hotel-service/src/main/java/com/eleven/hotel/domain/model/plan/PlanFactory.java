package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import org.apache.commons.lang3.Validate;

import java.util.Set;

public class PlanFactory {

    @SuppressWarnings("unused")
    @Builder(builderClassName = "Normal", builderMethodName = "normal", buildMethodName = "create")
    private static Plan createNormal(Long hotelId,
                                     StockAmount stockAmount,
                                     DateRange stayPeriod,
                                     Set<SaleChannel> saleChannels,
                                     DateTimeRange salePeriod,
                                     DateTimeRange preSellPeriod,
                                     PlanBasic basic) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stayPeriod, "sale period must not be null");
        Validate.notNull(stockAmount, "stock must not be null");
        Validate.isTrue(stockAmount.greaterThanZero(), "total must gather than zero");

        var plan = new Plan();
        plan.setHotelId(hotelId);
        plan.setStock(stockAmount);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setSaleChannels(saleChannels);
        plan.setBasic(basic);

        return plan;
    }

}

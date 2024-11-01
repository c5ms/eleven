package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.domain.model.SaleType;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;

@UtilityClass
public class PlanCreator {

    @Builder(builderClassName = "normalBuilder", builderMethodName = "normal", buildMethodName = "create")
    public static Plan createNormal(Integer hotelId,
                                    Stock stock,
                                    DateRange stayPeriod,
                                    DateTimeRange salePeriod,
                                    DateTimeRange preSellPeriod,
                                    PlanBasic basic) {

        Validate.notNull(hotelId, "hotelId must not be null");
        Validate.notNull(stock, "stock must not be null");
        Validate.isTrue(stock.greaterThanZero(), "total must gather than zero");

        var plan = new Plan(hotelId);
        plan.setStock(stock);
        plan.setSalePeriod(salePeriod);
        plan.setStayPeriod(stayPeriod);
        plan.setPreSalePeriod(preSellPeriod);
        plan.setSaleType(SaleType.NORMAL);
        plan.setSaleState(SaleState.STOPPED);
        plan.setBasic(basic);
        return plan;
    }

}

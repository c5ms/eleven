package com.motiveschina.hotel.features.plan;

import java.util.Set;
import com.motiveschina.hotel.common.DateRange;
import com.motiveschina.hotel.common.DateTimeRange;
import com.motiveschina.hotel.common.SaleChannel;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

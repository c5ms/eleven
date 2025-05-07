package com.motiveschina.hotel.features.plan;

import java.util.Set;
import com.motiveschina.hotel.core.DateRange;
import com.motiveschina.hotel.core.DateTimeRange;
import com.motiveschina.hotel.core.SaleChannel;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

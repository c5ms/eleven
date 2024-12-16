package com.eleven.travel.domain.plan;

import com.eleven.travel.core.DateRange;
import com.eleven.travel.core.DateTimeRange;
import com.eleven.travel.core.SaleChannel;

import java.util.Set;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

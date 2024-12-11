package com.eleven.domain.plan;

import com.eleven.common.DateRange;
import com.eleven.common.DateTimeRange;
import com.eleven.common.SaleChannel;

import java.util.Set;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

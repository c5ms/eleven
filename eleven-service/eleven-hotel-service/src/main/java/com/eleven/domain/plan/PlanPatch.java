package com.eleven.domain.plan;

import com.eleven.common.domain.DateRange;
import com.eleven.common.domain.DateTimeRange;
import com.eleven.common.domain.SaleChannel;

import java.util.Set;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

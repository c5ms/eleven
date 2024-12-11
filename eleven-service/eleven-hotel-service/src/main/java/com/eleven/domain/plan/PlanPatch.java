package com.eleven.domain.plan;

import com.eleven.core.DateRange;
import com.eleven.core.DateTimeRange;
import com.eleven.core.SaleChannel;
import com.eleven.domain.plan.values.PlanBasic;

import java.util.Set;

public interface PlanPatch {
    PlanBasic getBasic();

    Integer getStock();

    DateTimeRange getSalePeriod();

    DateTimeRange getPreSalePeriod();

    DateRange getStayPeriod();

    Set<SaleChannel> getChannels();
}

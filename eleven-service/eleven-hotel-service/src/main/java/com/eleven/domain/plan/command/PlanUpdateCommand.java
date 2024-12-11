package com.eleven.domain.plan.command;

import com.eleven.core.DateRange;
import com.eleven.core.DateTimeRange;
import com.eleven.core.SaleChannel;
import com.eleven.domain.plan.PlanPatch;
import com.eleven.domain.plan.values.PlanBasic;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class PlanUpdateCommand implements PlanPatch {
    private PlanBasic basic;
    private Integer stock;
    private DateTimeRange preSalePeriod;
    private DateTimeRange salePeriod;
    private DateRange stayPeriod;
    private Set<SaleChannel> channels;
    private Set<Long> rooms;
}

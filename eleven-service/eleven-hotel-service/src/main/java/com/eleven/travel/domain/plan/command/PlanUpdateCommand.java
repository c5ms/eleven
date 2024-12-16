package com.eleven.travel.domain.plan.command;

import com.eleven.travel.core.DateRange;
import com.eleven.travel.core.DateTimeRange;
import com.eleven.travel.core.SaleChannel;
import com.eleven.travel.domain.plan.PlanBasic;
import com.eleven.travel.domain.plan.PlanPatch;
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

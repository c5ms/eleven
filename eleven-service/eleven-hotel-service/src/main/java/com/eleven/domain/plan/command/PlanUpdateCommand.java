package com.eleven.domain.plan.command;

import com.eleven.common.DateRange;
import com.eleven.common.DateTimeRange;
import com.eleven.common.SaleChannel;
import com.eleven.domain.plan.PlanBasic;
import com.eleven.domain.plan.PlanPatch;
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

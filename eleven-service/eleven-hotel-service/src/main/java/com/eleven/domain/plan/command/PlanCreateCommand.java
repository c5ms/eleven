package com.eleven.domain.plan.command;

import com.eleven.core.DateRange;
import com.eleven.core.DateTimeRange;
import com.eleven.core.SaleChannel;
import com.eleven.domain.plan.PlanBasic;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PlanCreateCommand {

    private PlanBasic basic;
    private DateTimeRange salePeriod;
    private DateTimeRange preSalePeriod;

    private Integer stock;
    private DateRange stayPeriod;

    @Builder.Default
    private Set<SaleChannel> channels = new HashSet<>();

    @Builder.Default
    private Set<Long> rooms = new HashSet<>();

}

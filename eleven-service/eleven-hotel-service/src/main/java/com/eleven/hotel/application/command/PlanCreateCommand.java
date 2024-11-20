package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.model.plan.PlanBasic;
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

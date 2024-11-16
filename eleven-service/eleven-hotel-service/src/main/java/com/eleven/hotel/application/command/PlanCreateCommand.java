package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.values.DateRange;
import com.eleven.hotel.api.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.values.SaleChannel;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.api.domain.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PlanCreateCommand {

    private PlanBasic basic;
    private StockAmount stock;
    private DateTimeRange salePeriod;
    private DateTimeRange preSalePeriod;
    private DateRange stayPeriod;

    @Builder.Default
    private Set<SaleChannel> channels = new HashSet<>();

    @Builder.Default
    private Set<Long> rooms = new HashSet<>();

}

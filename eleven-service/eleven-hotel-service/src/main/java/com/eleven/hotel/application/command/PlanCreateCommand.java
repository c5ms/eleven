package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlanCreateCommand {
    private Stock stock;
    private DateTimeRange sellPeriod;
    private DateTimeRange preSellPeriod;
    private DateRange stayPeriod;
    private PlanBasic basic;
    private List<Integer> rooms;
}

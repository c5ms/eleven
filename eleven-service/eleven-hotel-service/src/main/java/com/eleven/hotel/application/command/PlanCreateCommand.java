package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanCreateCommand {
    private Stock stock;
    private DateTimeRange sellPeriod;
    private DateTimeRange preSellPeriod;
    private DateRange stayPeriod;
    private Plan.PlanBasic basic;
}

package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class PlanCreateCommand {
    private StockAmount stock;
    private DateTimeRange sellPeriod;
    private DateTimeRange preSellPeriod;
    private DateRange stayPeriod;
    private PlanBasic basic;
    private List<Long> rooms;
    private Set<SaleChannel> channels;
}

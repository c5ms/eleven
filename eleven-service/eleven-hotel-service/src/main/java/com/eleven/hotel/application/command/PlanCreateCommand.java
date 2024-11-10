package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<PlanAddRoomCommand> rooms= new ArrayList<>();
}

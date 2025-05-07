package com.motiveschina.hotel.features.plan.command;

import java.util.HashSet;
import java.util.Set;
import com.motiveschina.hotel.common.DateRange;
import com.motiveschina.hotel.common.DateTimeRange;
import com.motiveschina.hotel.common.SaleChannel;
import com.motiveschina.hotel.features.plan.PlanBasic;
import lombok.Builder;
import lombok.Getter;

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

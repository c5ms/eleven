package com.motiveschina.hotel.features.plan.command;

import java.util.Set;
import com.motiveschina.hotel.common.DateRange;
import com.motiveschina.hotel.common.DateTimeRange;
import com.motiveschina.hotel.common.SaleChannel;
import com.motiveschina.hotel.features.plan.PlanBasic;
import com.motiveschina.hotel.features.plan.PlanPatch;
import lombok.Builder;
import lombok.Getter;

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

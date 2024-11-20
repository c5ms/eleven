package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.domain.model.hotel.PlanBasic;
import com.eleven.hotel.domain.model.hotel.PlanPatch;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.DateTimeRange;
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

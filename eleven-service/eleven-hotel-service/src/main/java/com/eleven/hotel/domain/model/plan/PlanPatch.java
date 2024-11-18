package com.eleven.hotel.domain.model.plan;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.domain.model.hotel.values.DateRange;
import com.eleven.hotel.domain.model.hotel.values.DateTimeRange;
import com.eleven.hotel.domain.model.hotel.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PlanPatch {

    private PlanBasic basic;
    private StockAmount stock;
    private DateTimeRange salePeriod;
    private DateTimeRange preSalePeriod;
    private DateRange stayPeriod;

    @Builder.Default
    private Set<SaleChannel> channels = new HashSet<>();
}

package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.values.DateRange;
import com.eleven.core.domain.values.DateTimeRange;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.domain.values.StockAmount;
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

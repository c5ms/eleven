package com.eleven.travel.domain.plan;

import com.eleven.travel.core.*;
import com.eleven.travel.domain.plan.vo.PlanBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Schema(name = "Plan")
@Accessors(chain = true)
public final class PlanDto implements Serializable {

    private Long planId;
    private Long hotelId;

    private Integer stock;
    private SaleType saleType;
    private SaleState saleState;
    private Boolean isOnSale;

    @NotNull
    @JsonUnwrapped(prefix = "basic")
    private PlanBasicVo basic;

    @JsonUnwrapped(prefix = "perSalePeriod")
    private DateTimeRangeVo preSalePeriod;

    @NotNull
    @JsonUnwrapped(prefix = "salePeriod")
    private DateTimeRangeVo salePeriod;

    @NotNull
    @JsonUnwrapped(prefix = "stayPeriod")
    private DateRangeVo stayPeriod;

    private List<SaleChannel> channels;
}

package com.eleven.domain.plan.request;

import com.eleven.common.DateRangeVo;
import com.eleven.common.DateTimeRangeVo;
import com.eleven.common.SaleChannel;
import com.eleven.domain.plan.vo.PlanBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public final class PlanCreateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "basic")
    private PlanBasicVo basic;

    @JsonUnwrapped(prefix = "preSalePeriod")
    private DateTimeRangeVo preSalePeriod;

    @NotNull
    @JsonUnwrapped(prefix = "salePeriod")
    private DateTimeRangeVo salePeriod;

    @NotNull
    @JsonUnwrapped(prefix = "stayPeriod")
    private DateRangeVo stayPeriod;

    @NotNull
    @Min(1)
    @Schema(example = "200")
    private Integer stock;

    private Set<SaleChannel> channels = new HashSet<>();

    private Set<Long> rooms = new HashSet<>();

}

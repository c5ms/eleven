package com.motiveschina.hotel.features.plan.request;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.common.DateRangeVo;
import com.motiveschina.hotel.common.DateTimeRangeVo;
import com.motiveschina.hotel.common.SaleChannel;
import com.motiveschina.hotel.features.plan.vo.PlanBasicVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PlanUpdateRequest {

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

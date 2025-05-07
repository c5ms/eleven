package com.motiveschina.hotel.features.plan;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.common.DateRangeVo;
import com.motiveschina.hotel.common.DateTimeRangeVo;
import com.motiveschina.hotel.common.SaleChannel;
import com.motiveschina.hotel.common.SaleState;
import com.motiveschina.hotel.common.SaleType;
import com.motiveschina.hotel.features.plan.vo.PlanBasicVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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

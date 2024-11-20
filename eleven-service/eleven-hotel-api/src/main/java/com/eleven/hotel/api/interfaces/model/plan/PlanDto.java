package com.eleven.hotel.api.interfaces.model.plan;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.domain.enums.SaleState;
import com.eleven.hotel.api.domain.enums.SaleType;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.DateTimeRangeVo;
import com.eleven.hotel.api.interfaces.values.PlanBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PlanDto implements Serializable {

    private Long planId;
    private Long hotelId;

    private Integer stock;
    private SaleType type;
    private SaleState state;
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

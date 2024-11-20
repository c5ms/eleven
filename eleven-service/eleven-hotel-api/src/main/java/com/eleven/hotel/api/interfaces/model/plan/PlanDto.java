package com.eleven.hotel.api.interfaces.model.plan;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.api.domain.enums.SaleState;
import com.eleven.hotel.api.domain.enums.SaleType;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.DateTimeRangeVo;
import com.eleven.hotel.api.interfaces.values.PlanBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @JsonUnwrapped(prefix = "basic_")
    private PlanBasicVo basic;

    @JsonUnwrapped(prefix = "preSalePeriod_")
    private DateTimeRangeVo preSalePeriod;

    @JsonUnwrapped(prefix = "salePeriod_")
    private DateTimeRangeVo salePeriod;

    @JsonUnwrapped(prefix = "stayPeriod_")
    private DateRangeVo stayPeriod;

    private List<SaleChannel> channels;
}

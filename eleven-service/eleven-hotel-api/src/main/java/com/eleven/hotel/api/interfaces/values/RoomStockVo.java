package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "RoomStock")
public class RoomStockVo extends AbstractVo {

    @NotNull
    @JsonUnwrapped(prefix = "Available")
    private DateRangeVo availablePeriod;

    @Min(0)
    @Max(999)
    @Schema(example = "20")
    private Integer quantity;

}

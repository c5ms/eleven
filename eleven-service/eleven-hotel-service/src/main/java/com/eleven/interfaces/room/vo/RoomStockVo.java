package com.eleven.interfaces.room.vo;

import com.eleven.common.DateRangeVo;
import com.eleven.common.layer.Vo;
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
public class RoomStockVo extends Vo {

    @NotNull
    @JsonUnwrapped(prefix = "Available")
    private DateRangeVo availablePeriod;

    @Min(0)
    @Max(999)
    @Schema(example = "20")
    private Integer quantity;

}

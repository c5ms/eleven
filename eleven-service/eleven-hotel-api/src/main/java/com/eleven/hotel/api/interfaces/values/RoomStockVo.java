package com.eleven.hotel.api.interfaces.values;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "RoomStock")
@Accessors(chain = true)
public class RoomStockVo {

    @NotNull
    @JsonUnwrapped(prefix = "available_")
    private DateRangeVo availablePeriod;

    @Min(0)
    @Max(999)
    @Schema(example = "20")
    private Integer quantity;

}

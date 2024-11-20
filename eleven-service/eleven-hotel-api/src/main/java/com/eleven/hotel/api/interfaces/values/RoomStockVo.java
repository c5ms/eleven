package com.eleven.hotel.api.interfaces.values;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "RoomStock")
@Accessors(chain = true)
public class RoomStockVo extends AbstractVo {

    @NotNull
    @JsonUnwrapped(prefix = "Available")
    private DateRangeVo availablePeriod;

    @Min(0)
    @Max(999)
    @Schema(example = "20")
    private Integer quantity;

}

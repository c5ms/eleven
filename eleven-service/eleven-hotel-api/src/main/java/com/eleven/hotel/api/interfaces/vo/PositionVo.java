package com.eleven.hotel.api.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "Position")
@Accessors(chain = true)
public class PositionVo{

    @Schema(example = "100.56666")
    private Double latitude;

    @Schema(example = "110.9876")
    private Double longitude;
}

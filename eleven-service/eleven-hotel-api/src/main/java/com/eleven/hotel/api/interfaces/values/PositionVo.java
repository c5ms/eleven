package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Position")
@Accessors(chain = true)
public class PositionVo extends AbstractVo {

    @Schema(example = "100.56666")
    private Double latitude;

    @Schema(example = "110.9876")
    private Double longitude;
}

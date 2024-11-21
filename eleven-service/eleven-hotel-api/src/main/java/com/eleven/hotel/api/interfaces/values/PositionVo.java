package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Position")
public class PositionVo extends AbstractVo {

    @Schema(example = "100.56666")
    private Double latitude;

    @Schema(example = "110.9876")
    private Double longitude;
}

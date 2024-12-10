package com.eleven.domain.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Position")
public class PositionVo extends Vo {

    @Schema(example = "100.56666")
    private Double latitude;

    @Schema(example = "110.9876")
    private Double longitude;
}

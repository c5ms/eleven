package com.eleven.travel.domain.hotel.vo;

import com.eleven.travel.core.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "Position")
public class PositionVo extends Vo {

    @NotNull
    @Schema(example = "100.56666")
    private Double latitude;

    @NotNull
    @Schema(example = "110.9876")
    private Double longitude;
}

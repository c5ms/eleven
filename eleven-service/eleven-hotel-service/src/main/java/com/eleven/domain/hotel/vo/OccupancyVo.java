package com.eleven.domain.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Occupancy")
public class OccupancyVo extends Vo {

    @Schema(example = "1")
    private Integer minPerson;

    @Schema(example = "5")
    private Integer maxPerson;
}

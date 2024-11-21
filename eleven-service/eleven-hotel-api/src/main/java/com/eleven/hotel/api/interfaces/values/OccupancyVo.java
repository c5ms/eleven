package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Occupancy")
public class OccupancyVo extends AbstractVo {

    @Schema(example = "1")
    private Integer minPerson;

    @Schema(example = "5")
    private Integer maxPerson;
}

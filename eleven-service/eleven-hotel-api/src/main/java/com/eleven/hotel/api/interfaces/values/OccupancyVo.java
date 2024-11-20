package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Occupancy")
@Accessors(chain = true)
public class OccupancyVo extends AbstractVo {

    @Schema(example = "1")
    private Integer minPerson;

    @Schema(example = "5")
    private Integer maxPerson;
}

package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "Occupancy")
@Accessors(chain = true)
public class OccupancyVo {

    private Integer minPerson;
    private Integer maxPerson;
}

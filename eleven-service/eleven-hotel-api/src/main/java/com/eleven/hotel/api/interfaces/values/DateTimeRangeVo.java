package com.eleven.hotel.api.interfaces.values;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(name = "DataTimeRange")
@Accessors(chain = true)
public class DateTimeRangeVo {

    @Schema(example = "2025-01-01 08:00")
    private LocalDateTime start;

    @Schema(example = "2025-03-31 20:00")
    private LocalDateTime end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

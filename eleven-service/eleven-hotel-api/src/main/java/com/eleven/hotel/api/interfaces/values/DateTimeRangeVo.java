package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "DataTimeRange")
public class DateTimeRangeVo extends AbstractVo {

    @Schema(example = "2025-01-01 08:00")
    private LocalDateTime start;

    @Schema(example = "2025-03-31 20:00")
    private LocalDateTime end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

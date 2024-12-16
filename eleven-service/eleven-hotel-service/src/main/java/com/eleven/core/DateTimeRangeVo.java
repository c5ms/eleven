package com.eleven.core;

import com.eleven.core.layer.Vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "DataTimeRange")
public class DateTimeRangeVo extends Vo {

    @Schema(example = "2025-01-01 08:00")
    private LocalDateTime start;

    @Schema(example = "2025-03-31 20:00")
    private LocalDateTime end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

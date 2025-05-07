package com.motiveschina.hotel.common;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.motiveschina.hotel.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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

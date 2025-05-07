package com.motiveschina.hotel.common;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.motiveschina.hotel.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "DataRange")
public class DateRangeVo extends Vo {

    @Schema(example = "2025-01-01")
    private LocalDate start;

    @Schema(example = "2025-03-31")
    private LocalDate end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

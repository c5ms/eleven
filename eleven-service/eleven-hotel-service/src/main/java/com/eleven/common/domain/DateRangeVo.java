package com.eleven.common.domain;

import com.eleven.common.layer.Vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

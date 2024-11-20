package com.eleven.hotel.api.interfaces.values;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Schema(name = "DataRange")
@Accessors(chain = true)
public class DateRangeVo extends AbstractVo {

    @Schema(example = "2025-01-01")
    private LocalDate start;

    @Schema(example = "2025-03-31")
    private LocalDate end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

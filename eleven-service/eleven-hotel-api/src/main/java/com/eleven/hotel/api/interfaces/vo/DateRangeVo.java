package com.eleven.hotel.api.interfaces.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Schema(name = "DataRange")
@Accessors(chain = true)
public class DateRangeVo {

    private LocalDate start;
    private LocalDate end;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isCurrent;

}

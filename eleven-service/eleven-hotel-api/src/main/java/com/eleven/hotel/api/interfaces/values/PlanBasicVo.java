package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "PlanBasic")
@Accessors(chain = true)
public class PlanBasicVo {

    @NotBlank
    @Schema(example = "example plan")
    private String name;

    @NotBlank
    @Schema(example = "example plan description")
    private String desc;

}

package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "PlanBasic")
@Accessors(chain = true)
public class PlanBasicVo extends AbstractVo {

    @NotBlank
    @Schema(example = "example plan")
    private String name;

    @NotBlank
    @Schema(example = "example plan description")
    private String desc;

}

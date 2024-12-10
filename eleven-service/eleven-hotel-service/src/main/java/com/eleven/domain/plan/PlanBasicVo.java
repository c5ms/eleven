package com.eleven.domain.plan;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PlanBasic")
public class PlanBasicVo extends Vo {

    @NotBlank
    @Schema(example = "example plan")
    private String name;

    @NotBlank
    @Schema(example = "example plan description")
    private String desc;

}

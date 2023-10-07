package com.eleven.doney.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSaveAction {

    @Schema(description = "项目标题")
    @NotBlank(message = "项目标题不能为空")
    @Size(min = 1, max = 200, message = "项目标题必须在 1-200 长度内")
    private String title;

    @Schema(description = "项目标识")
    @NotBlank(message = "项目标识不能为空")
    @Size(min = 3, max = 200, message = "项目标识必须在 3-200 长度内")
    private String code;

    @Schema(description = "项目描述")
    @Size(min = 1, max = 2000, message = "项目描述必须在 1-2000 长度内")
    private String description;

    @Schema(description = "项目状态")
    @NotBlank(message = "项目状态不能为空")
    private String state = "pending";

    @Schema(description = "项目主页")
    @Size(min = 1, max = 500, message = "项目主页必须在 1-500 长度内")
    private String url;

}

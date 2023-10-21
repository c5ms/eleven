package com.eleven.doney.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IssueSaveAction {

    @Schema(description = "任务标题")
    @NotBlank(message = "任务标题不能为空")
    @Size(min = 1, max = 200, message = "任务标题必须在 1-200 长度内")
    private String title;

    @Schema(description = "任务描述")
    @Size(min = 1, max = 2000, message = "任务描述必须在 1-2000 长度内")
    private String description;

    @Schema(description = "任务状态")
    @NotBlank(message = "任务状态不能为空")
    private String state = "pending";

    @Schema(description = "项目")
    @NotBlank(message = "项目不能为空")
    String projectId;

}

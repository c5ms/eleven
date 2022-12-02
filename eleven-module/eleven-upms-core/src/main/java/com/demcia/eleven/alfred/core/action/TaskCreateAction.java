package com.demcia.eleven.alfred.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskCreateAction {

    @Schema(description = "任务主题")
    @NotBlank(message = "任务主题不能为空")
    private String subject;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "主办人")
    private String directorId;

    @Schema(description = "协助人")
    private String assistantId;

    @Schema(description = "截止时间")
    private LocalDate deadline;

    @Schema(description = "状态")
    private String state;

}

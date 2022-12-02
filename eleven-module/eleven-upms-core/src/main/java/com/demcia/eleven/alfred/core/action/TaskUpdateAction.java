package com.demcia.eleven.alfred.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskUpdateAction {

    @NotBlank(message = "任务主题不能为空")
    private String subject;

    @Schema(description = "任务描述")
    private String description;

}

package com.eleven.doney.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private String id;

    private String title;

    private String description;

    private String state;

    private LocalDateTime lastHandleTime;

    private LocalDateTime createAt;

    private ProjectSummary project;

    @Schema(description = "进度百分比")
    private Integer progress;
}

package com.eleven.access.admin.endpoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SyncStaticsDto {
    @Schema(description = "任务数量")
    private long taskCount;
    @Schema(description = "启动的任务数")
    private long taskStartedCount;
    @Schema(description = "最近错误数")
    private long recentlyErrorCount;
}

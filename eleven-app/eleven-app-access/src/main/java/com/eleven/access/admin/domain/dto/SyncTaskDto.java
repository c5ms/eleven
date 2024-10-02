package com.eleven.access.admin.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SyncTaskDto implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "计划ID")
    private String id;

    @Schema(description = "计划名")
    @NotBlank(message = "计划名称不能为空")
    @Size(min = 2, max = 100, message = "长度在2到100之间")
    private String title;

    @Schema(description = "备注描述")
    @Size(min = 2, max = 500, message = "长度在2到500之间")
    private String memo;

    @Schema(description = "Cron 计划")
    @Size(min = 2, max = 100, message = "长度在2到100之间")
    private String cron;

    @Schema(description = "调度间隔")
    private Long interval;

    @NotBlank(message = "计划必须要有数据来源")
    @Schema(description = "来源类型")
    private String readerType;

    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "每天时间")
    private LocalTime dailyTime;

    @Schema(description = "来源配置")
    private Map<String, String> readerConfig = new HashMap<>();

    @Schema(description = "来源运行")
    private Map<String, String> readerRuntime = new HashMap<>();

    @Schema(description = "目标类型")
    private String writerType;

    @Schema(description = "写入目标")
    private List<SyncTaskWriterDto> writers = new ArrayList<>();

    @Schema(description = "字段映射")
    private List<SyncTaskMappingDto> mappings = new ArrayList<>();
}

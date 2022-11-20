package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.persist.jpa.convert.StringListConverter;
import com.cnetong.common.persist.jpa.convert.StringStringMapConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽取计划
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Table(name = "sync_task")
public class SyncTask extends AbstractDomain {

    @Column(name = "name_")
    private String title;

    @Column(name = "memo_")
    private String memo;

    @Column(name = "interval_")
    private Long interval;

    @Column(name = "last_execute_time_")
    private LocalDateTime lastExecuteTime;

    @Column(name = "cron_")
    private String cron;

    @Convert(converter = StringListConverter.class)
    @Column(name = "collections_")
    private List<String> collections;

    @Column(name = "reader_type_")
    private String readerType;

    @Schema(description = "异常信息")
    @Column(name = "error", length = 1024)
    private String error;

    @Schema(description = "是否健康")
    @Column(name = "health")
    private boolean health;

    @Schema(description = "下次执行时间")
    @Column(name = "execution_time_")
    private LocalDateTime executionTime;

    @Lob
    @Column(name = "reader_config_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> readerConfig = new HashMap<>();

    @Lob
    @Column(name = "reader_runtime_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> readerRuntime = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id_")
    private List<SyncTaskWriter> writers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id_")
    private List<SyncTaskMapping> mappings = new ArrayList<>();

    @Schema(description = "发布状态")
    @Column(name = "started_", nullable = false)
    private boolean started = false;

    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "每天时间")
    @Column(name = "daily_time_")
    private LocalTime dailyTime;

    @Schema(description = "正在运行")
    @Column(name = "running_", nullable = false)
    private boolean running = false;

    @Schema(description = "当前处理完成")
    @Column(name = "currentCompleted_", nullable = true)
    private Long currentCompleted;

    public void publish() {
        this.setStarted(true);
    }

    public void cancel() {
        this.setStarted(false);
    }
}

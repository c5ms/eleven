package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "sync_log")
public class SyncLog extends AbstractIdDomain {
    private static final long serialVersionUID = 1L;

    @Column(name = "schedule_id_")
    private String scheduleId;

    @Column(name = "time_start_")
    private LocalDateTime startTime;

    @Column(name = "time_end_")
    private LocalDateTime endTime;

    @Schema(description = "消耗时间（毫秒）")
    @Column(name = "duration_")
    private Long duration;

    @Column(name = "total_count_")
    private Long totalCount;

    @Schema(description = "异常信息")
    @Column(name = "exception_message_", length = 1024)
    private String exceptionMessage;

    @Lob
    @Schema(description = "异常堆栈")
    @Column(name = "exception_stack_")
    private String exceptionStack;

    @Column(name = "success_")
    private Boolean success;

}

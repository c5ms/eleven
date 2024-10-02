package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "应用接口")
@Table(name = "gate_log_access",indexes = {
        @Index(name="idx_gate_log_access_01",columnList = "access_time_"),
        @Index(name="idx_gate_log_access_02",columnList = "trace_id_,app_id_,api_id_,response_status_,success_")
})
public class GateApiAccessLog extends AbstractIdDomain {

    @Schema(description = "应用ID")
    @Column(name = "app_id_", length = 200)
    private String appId;

    @Schema(description = "路由ID")
    @Column(name = "api_id_", length = 200)
    private String apiId;

    @Schema(description = "分组ID")
    @Column(name = "group_id_", length = 200)
    private String groupId;

    @Schema(description = "TRACE_ID")
    @Column(name = "trace_id_", length = 200)
    private String traceId;

    @Schema(description = "访问时间")
    @Column(name = "access_time_")
    private LocalDateTime accessTime;

    @Schema(description = "耗时")
    @Column(name = "duration_")
    private Duration duration;

    @Schema(description = "IP")
    @Column(name = "remote_ip_")
    private String remoteIp;

    @Schema(description = "响应状态")
    @Column(name = "response_status_")
    private Integer responseStatus;

    @Schema(description = "成功")
    @Column(name = "success_")
    private Boolean success;

    @Schema(description = "错误")
    @Column(name = "error_",length = 100)
    private String error;
}

package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "gate_request_summary")
public class GateRequestSummary extends AbstractIdDomain {

    @Schema(description = "请求数")
    @Column(name = "request_count_")
    private Long requestCount;

    @Schema(description = "异常数量")
    @Column(name = "error_count_")
    private Long errorCount;

    @Schema(description = "路由数量")
    @Column(name = "route_count_")
    private Long routeCount;

    @Schema(description = "统计日期")
    @Column(name = "statics_date_")
    private LocalDate staticDate;

    @Schema(description = "统计开始时间")
    @Column(name = "statics_begin_date_time_")
    private LocalDateTime staticBeginDateTime;

    @Schema(description = "统计结束时间")
    @Column(name = "statics_end_date_time_")
    private LocalDateTime staticEndDateTime;

}

package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.domain.entity.GateApiAccessLog;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class GateApiLogQueryAction extends JpaPageableQueryAction<GateApiAccessLog> {

    @Schema(description = "开始时间")
    private LocalDate timeStart;

    @Schema(description = "结束时间")
    private LocalDate timeEnd;

    @Schema(description = "应用标识")
    private String appId;

    @Schema(description = "接口标识")
    private String apiId;

    @Schema(description = "响应状态")
    private Integer responseStatus;

    @Schema(description = "处理状态")
    private Boolean success;

    @Schema(description = "trace id")
    private String traceId;

    @Override
    public Specification<GateApiAccessLog> toSpecification() {
        if(ObjectUtils.anyNull(timeStart,timeEnd)){
            throw ProcessRejectException.of("请指定查询的时间范围");
        }
        return Specifications.<GateApiAccessLog>and()
                .eq(StringUtils.isNotBlank(appId), GateApiAccessLog.Fields.appId, StringUtils.trim(appId))
                .eq(StringUtils.isNotBlank(apiId), GateApiAccessLog.Fields.apiId, StringUtils.trim(apiId))
                .eq(ObjectUtils.allNotNull(responseStatus), GateApiAccessLog.Fields.responseStatus, responseStatus)
                .eq(ObjectUtils.allNotNull(success), GateApiAccessLog.Fields.success, success)
                .eq(ObjectUtils.allNotNull(traceId), GateApiAccessLog.Fields.traceId, traceId)
                .ge(Objects.nonNull(timeStart), GateApiAccessLog.Fields.accessTime, timeStart.atTime(LocalTime.MIN))
                .le(Objects.nonNull(timeEnd), GateApiAccessLog.Fields.accessTime, timeEnd.atTime(LocalTime.MAX))
                .build();
    }

}

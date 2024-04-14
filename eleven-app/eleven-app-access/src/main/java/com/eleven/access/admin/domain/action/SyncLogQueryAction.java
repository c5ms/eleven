package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class SyncLogQueryAction extends JpaPageableQueryAction<SyncLog> {

    @Schema(description = "时间范围起始")
    private LocalDateTime timeStart;

    @Schema(description = "时间范围截止")
    private LocalDateTime timeEnd;

    @Schema(description = "同步结果")
    private Boolean success;


    @Override
    public Specification<SyncLog> toSpecification() {
        return Specifications.<SyncLog>and()
                .ge(Objects.nonNull(timeStart), SyncLog.Fields.startTime, timeStart)
                .le(Objects.nonNull(timeEnd), SyncLog.Fields.startTime, timeEnd)
                .eq(Objects.nonNull(success), SyncLog.Fields.success, success)
                .build();
    }
}

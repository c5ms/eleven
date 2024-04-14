package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.action.SyncLogQueryAction;
import com.cnetong.access.admin.domain.dto.SyncLogDetail;
import com.cnetong.access.admin.domain.entity.SyncLog;
import com.cnetong.access.admin.service.SyncLogService;
import com.cnetong.access.admin.service.SyncTaskService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.time.TimeService;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "syncLog", description = "数据同步日志")
@RestApi
@RestController
@RequiredArgsConstructor
@RequestMapping("/sync/logs")
public class SyncLogResourceV1 {

    private final TimeService timeService;
    private final SyncLogService syncLogService;
    private final SyncTaskService syncTaskService;

    @Operation(summary = "查询系统日志")
    @GetMapping
    public Page<SyncLogDetail> queryLogs(@ParameterObject SyncLogQueryAction action) {
        action.setSort(SyncLog.Fields.startTime);
        Page<SyncLog> scheduleLogs = syncLogService.query(action);
        return scheduleLogs.map(scheduleLog -> {
            SyncLogDetail detail = new SyncLogDetail();
            BeanUtils.copyProperties(scheduleLog, detail);
            syncTaskService.getTask(scheduleLog.getScheduleId()).ifPresent(schedule -> {
                detail.setTaskTitle(schedule.getTitle());
            });
            return detail;
        });
    }

    @Operation(summary = "清理数据(30天前)")
    @PostMapping("/clean")
    public void cleanLogs() {
        syncLogService.deleteDaysAgo(timeService.getLocalDateTime().minusDays(30));
    }

}

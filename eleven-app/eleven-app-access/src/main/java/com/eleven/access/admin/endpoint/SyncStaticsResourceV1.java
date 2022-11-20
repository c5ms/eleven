package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.endpoint.dto.SyncStaticsDto;
import com.cnetong.access.admin.service.SyncStatistician;
import com.cnetong.common.time.TimeContext;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestApi
@Tag(name = "syncStatics", description = "同步统计")
@PreAuthorize("isAuthenticated()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sync/statics")
public class SyncStaticsResourceV1 {
    private final SyncStatistician syncStatistician;

    @Operation(summary = "统计信息")
    @GetMapping
    public SyncStaticsDto statistics() {
        return new SyncStaticsDto()
                .setTaskCount(syncStatistician.statCount())
                .setTaskStartedCount(syncStatistician.statStartedCount())
                .setRecentlyErrorCount(syncStatistician.statRecentlyErrorCount(TimeContext.localDateTime().minusDays(7)));
    }

}

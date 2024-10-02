package com.eleven.gateway.admin.domain.statics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class RequestSummary {

    private Long requestCount;
    private Long routeCount;
    private Long errorCount;
    private LocalDate staticDate;

    public Long getSuccessCount() {
        return getRouteCount() - getErrorCount();
    }
}

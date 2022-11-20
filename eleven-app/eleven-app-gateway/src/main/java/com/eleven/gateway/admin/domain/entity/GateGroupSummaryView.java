package com.eleven.gateway.admin.domain.entity;

import java.time.LocalDate;

public interface GateGroupSummaryView {

    Long getRequestCount();

    Long getRouteCount();

    Long getErrorCount();

    LocalDate getStaticDate();
}

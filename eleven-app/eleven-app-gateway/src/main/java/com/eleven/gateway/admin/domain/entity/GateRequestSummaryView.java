package com.eleven.gateway.admin.domain.entity;

import java.time.LocalDate;

public interface GateRequestSummaryView {

    Long getRequestCount();

    Long getRouteCount();

    Long getErrorCount();

    LocalDate getStaticDate();
}

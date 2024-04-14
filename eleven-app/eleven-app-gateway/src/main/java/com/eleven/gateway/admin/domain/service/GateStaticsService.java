package com.eleven.gateway.admin.domain.service;

import com.eleven.gateway.admin.domain.statics.QpsSummary;
import com.eleven.gateway.admin.domain.statics.RequestSummary;
import com.eleven.gateway.admin.domain.statics.RouteSummary;

import java.time.LocalDate;
import java.util.List;

public interface GateStaticsService {

    List<QpsSummary> qps();

    RequestSummary requests(LocalDate staticDate);

    List<RouteSummary> routes();
}

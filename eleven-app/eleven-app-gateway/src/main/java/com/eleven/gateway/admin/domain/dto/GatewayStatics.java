package com.eleven.gateway.admin.domain.dto;

import com.eleven.gateway.admin.domain.statics.QpsSummary;
import com.eleven.gateway.admin.domain.statics.RequestSummary;
import com.eleven.gateway.admin.domain.statics.RouteSummary;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GatewayStatics {
    private List<QpsSummary> qps;
    private RequestSummary requests;
    private List<RouteSummary> routes;
    private long numberApis;
    private long numberRoutes;
    private long numberStacks;
    private long numberServices;
}

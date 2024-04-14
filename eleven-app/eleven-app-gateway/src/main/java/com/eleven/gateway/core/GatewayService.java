package com.eleven.gateway.core;

import java.util.List;

public interface GatewayService {
    String getId();

    String getName();

    List<RouteFilter> getFilters();

    String selectUri();
}

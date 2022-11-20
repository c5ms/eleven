package com.eleven.gateway.admin.support;

import com.eleven.gateway.core.GatewayRouteUpstream;
import com.eleven.gateway.core.GatewayService;
import com.eleven.gateway.core.RouteFilter;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
public class DefaultGatewayService implements GatewayService {

    String id;
    String name;
    GatewayRouteUpstream upstream;
    List<RouteFilter> filters;

    @Builder
    public DefaultGatewayService(String id,
                                 String name,
                                 List<RouteFilter> filters,
                                 GatewayRouteUpstream upstream) {
        this.id = id;
        this.name = name;
        this.filters = filters;
        this.upstream = upstream;
    }

    @Override
    public String selectUri() {
        return this.upstream.selectUri();
    }
}

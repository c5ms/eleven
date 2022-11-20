package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class GatewayRouteService {
    String id;
    String path;

    @Builder
    public GatewayRouteService(String id, String path) {
        this.id = id;
        this.path = StringUtils.defaultString(path, "/");
    }
}

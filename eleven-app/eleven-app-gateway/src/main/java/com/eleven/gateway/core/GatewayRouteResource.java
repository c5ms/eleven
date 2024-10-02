package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class GatewayRouteResource {
    String id;
    String path;

    @Builder
    public GatewayRouteResource(String id, String path) {
        this.id = id;
        this.path = StringUtils.defaultString(path, "/");
    }
}

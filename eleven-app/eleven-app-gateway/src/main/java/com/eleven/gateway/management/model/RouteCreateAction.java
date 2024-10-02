package com.eleven.gateway.management.model;

import com.eleven.gateway.core.GatewayRouteProxyMode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class RouteCreateAction {

    private String name;

    private String description;

    private String stackId;

    private String path;

    private GatewayRouteProxyMode proxyMode = GatewayRouteProxyMode.dynamicProxy;

    private Integer sort;

    private String host;

    private String method;

    private String contentType;

    private String serviceId;

    private String servicePath;

    private String resourceId;

    private String resourcePath;

    private List<String> uris = new ArrayList<>();

    private List<String> filters = new ArrayList<>();

    private List<String> predicates = new ArrayList<>();
}

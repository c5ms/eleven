package com.eleven.gateway.core;

import org.springframework.web.server.ServerWebExchange;

public interface RoutePredicate {
    boolean test(ServerWebExchange exchange);

    String getName();

    String getConfig();

}

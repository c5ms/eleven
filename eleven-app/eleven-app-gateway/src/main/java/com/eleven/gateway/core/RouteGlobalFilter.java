package com.eleven.gateway.core;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RouteGlobalFilter extends RouteFilter {

    Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain);

    default String getName() {
        return null;
    }

    default String getConfig() {
        return null;
    }
}

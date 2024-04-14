package com.eleven.gateway.core;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RouteFilter {

    Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain);

    String getName();

    String getConfig();
}

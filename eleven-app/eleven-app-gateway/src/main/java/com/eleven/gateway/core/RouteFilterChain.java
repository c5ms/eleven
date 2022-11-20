package com.eleven.gateway.core;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class RouteFilterChain {

    private final int index;
    private final List<RouteFilter> filters;

    public RouteFilterChain(List<RouteFilter> filters) {
        this.filters = filters;
        this.index = 0;
    }

    private RouteFilterChain(RouteFilterChain parent, int index) {
        this.filters = parent.getFilters();
        this.index = index;
    }

    public List<RouteFilter> getFilters() {
        return filters;
    }

    public Mono<Void> filter(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            if (this.index < filters.size()) {
                RouteFilter filter = filters.get(this.index);
                RouteFilterChain chain = new RouteFilterChain(this, this.index + 1);
                return filter.filter(exchange, chain);
            } else {
                return Mono.empty(); // complete
            }
        });
    }
}

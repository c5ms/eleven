package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
public class RequestUrlRouteFilter implements RouteGlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        GatewayRoute gatewayRoute = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_ROUTE_ATTR);

        // 默认目标
        Optional<URI> routeUriOpt = gatewayRoute.route(exchange);
        if (routeUriOpt.isEmpty()) {
            return GatewayServerUtil.response(exchange, GatewayError.NO_ROUTE_TARGET);
        }
        if (!routeUriOpt.get().isAbsolute()) {
            return GatewayServerUtil.response(exchange, GatewayError.ERROR_ROUTE_TARGET);
        }

        var uri = routeUriOpt.get();
        exchange.getAttributes().put(GatewayConstants.GATEWAY_REQUEST_URL_ATTR, uri);

        if (log.isDebugEnabled())
            log.debug("route {} method from {} to {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI(), uri);

        return chain.filter(exchange);
    }


}

package com.eleven.gateway.supportT;

import com.eleven.gateway.core.GatewayObserver;
import com.eleven.gateway.core.GatewayRoute;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
@Component
public class DefaultGatewayObserver implements GatewayObserver {
    @Override
    public void onRequest(ServerWebExchange exchange) {

    }

    @Override
    public void onRoute(ServerWebExchange exchange, GatewayRoute gatewayRoute) {

    }

    @Override
    public void onError(ServerWebExchange exchange, GatewayRoute gatewayRoute, Throwable throwable) {

    }

    @Override
    public void onRouteSuccess(ServerWebExchange exchange, GatewayRoute gatewayRoute) {

    }

    @Override
    public void onRouteFailure(ServerWebExchange exchange, GatewayRoute route) {

    }
}

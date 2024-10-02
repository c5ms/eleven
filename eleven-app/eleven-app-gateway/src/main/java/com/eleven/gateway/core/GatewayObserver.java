package com.eleven.gateway.core;

import org.springframework.web.server.ServerWebExchange;

/**
 * 网关观察员
 */
public interface GatewayObserver {
    /**
     * 请求
     */
    void onRequest(ServerWebExchange exchange);

    /**
     * 命中
     */
    void onRoute(ServerWebExchange exchange, GatewayRoute gatewayRoute);

    /**
     * 异常
     */
    void onError(ServerWebExchange exchange, GatewayRoute gatewayRoute, Throwable throwable);

    /**
     * 路由成功
     */
    void onRouteSuccess(ServerWebExchange exchange, GatewayRoute gatewayRoute);

    /**
     * 路由失败
     */
    void onRouteFailure(ServerWebExchange exchange, GatewayRoute route);

}

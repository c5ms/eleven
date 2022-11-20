package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class GatewayStack {
    private final String id;
    private final String name;
    private final List<RoutePredicate> predicates;
    private final List<GatewayRoute> routes;

    @Builder
    public GatewayStack(String id,
                        String name,
                        List<RoutePredicate> predicates,
                        List<GatewayRoute> routes) {
        Assert.hasText(id, "一个服务必须有一个 ID");
        Assert.hasText(name, "一个服务必须有一个名字");
        this.id = id;
        this.name = name;
        this.predicates = Optional.ofNullable(predicates).orElseGet(ArrayList::new);
        this.routes = Optional.ofNullable(routes).orElseGet(ArrayList::new);
    }

    /**
     * 匹配一次请求
     *
     * @param exchange 请求
     * @return true 可处理
     */
    public boolean match(ServerWebExchange exchange) {
        return getPredicates()
            .stream()
            .allMatch(routePredicate -> routePredicate.test(exchange));
    }

    /**
     * 匹配命中的路由
     *
     * @param exchange 请求
     * @return 命中的路由
     */
    public Optional<GatewayRoute> matchRoute(ServerWebExchange exchange) {
        return this.getRoutes().stream()
            .filter(gatewayRoute -> gatewayRoute.match(exchange))
            .findFirst();
    }

}

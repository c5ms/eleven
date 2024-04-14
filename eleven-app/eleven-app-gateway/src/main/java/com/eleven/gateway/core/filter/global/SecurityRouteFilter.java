package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.GatewayConstants;
import com.eleven.gateway.core.GatewayRoute;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteGlobalFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityRouteFilter implements RouteGlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        GatewayRoute route = (GatewayRoute) exchange.getAttributes().get(GatewayConstants.GATEWAY_ROUTE_ATTR);

        // 是否需要做安全检查
//        if (route.isSecured()) {
//            String key = exchange.getRequest().getHeaders().getFirst(GatewayConstants.GATEWAY_HEADER_GATEWAY_KEY);
//            if (StringUtils.isEmpty(key)) {
//                return GatewayServerUtil.response(exchange, GatewayError.UN_AUTHORIZED_REQUEST);
//            }
//        }

        return chain.filter(exchange);
    }
}

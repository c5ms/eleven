package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.*;
import com.eleven.gateway.core.utils.GatewayNettyDataBufferUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class StaticResourceRouteFilter implements RouteGlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        GatewayRoute gatewayRoute = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_ROUTE_ATTR);

        // 如果已经处理了 则放弃
        if (GatewayServerUtil.isAlreadyRouted(exchange)) {
            return chain.filter(exchange);
        }

        return gatewayRoute.matchResource(exchange)
            .map(content -> {
                GatewayServerUtil.setAlreadyRouted(exchange);
                exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_ENCODING, content.getEncoding());
                exchange.getResponse().getHeaders().setContentType(content.getMediaType());
                exchange.getResponse().getHeaders().setCacheControl(content.getCacheControl());
                return exchange.getResponse().writeWith(GatewayNettyDataBufferUtils.read(content.getBody()));
            })
            .orElseGet(() -> chain.filter(exchange));
    }


}

package com.eleven.gateway.core.filter;

import com.eleven.gateway.core.GatewayConstants;
import com.eleven.gateway.core.RouteFilter;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreserveHostHeaderRouteFilterFactory implements RouteFilterFactory {

    private static final String NAME = "preserveHost";

    public String getName() {
        return NAME;
    }

    @Override
    public RouteFilter apply(String configString) {
        return new RouteFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
                exchange.getAttributes().put(GatewayConstants.PRESERVE_HOST_HEADER_ATTRIBUTE, true);
                return chain.filter(exchange);
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public String getConfig() {
                return "";
            }

        };
    }


}

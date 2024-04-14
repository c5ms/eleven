package com.eleven.gateway.core.filter;

import cn.hutool.json.JSONUtil;
import com.eleven.gateway.core.GatewayServerUtil;
import com.eleven.gateway.core.RouteFilter;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteFilterFactory;
import com.eleven.gateway.core.utils.ConfigMap;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class AddResponseHeaderRouteFilterFactory implements RouteFilterFactory {
    private static final String NAME = "addResponseHeader";

    public String getName() {
        return NAME;
    }

    @Override
    public RouteFilter apply(String configString) {
        ConfigMap config = JSONUtil.toBean(configString, ConfigMap.class);

        return new RouteFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
                HttpHeaders headers = exchange.getResponse().getHeaders();
                for (Map.Entry<String, String> entry : config.entrySet()) {
                    String value = GatewayServerUtil.expand(exchange, entry.getValue());
                    headers.add(entry.getKey(), value);
                }
                return chain.filter(exchange);
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public String getConfig() {
                return configString;
            }

        };
    }


}

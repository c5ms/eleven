package com.eleven.gateway.core.predicate;

import cn.hutool.json.JSONUtil;
import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import com.eleven.gateway.core.utils.ConfigMap;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeaderRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "header";

    public String getName() {
        return NAME;
    }

    @Override
    public RoutePredicate apply(String configString) {
        ConfigMap config = JSONUtil.toBean(configString, ConfigMap.class);
        var pattens = config.entrySet().stream()
            .map(stringStringEntry -> new Config(stringStringEntry.getKey(), stringStringEntry.getValue()))
            .collect(Collectors.toList());
        return new RoutePredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                for (Config patten : pattens) {
                    List<String> values = exchange.getRequest().getHeaders().getOrDefault(patten.getName(), Collections.emptyList());
                    if (values.isEmpty()) {
                        return false;
                    }
                    for (String value : values) {
                        if (value.matches(patten.getRegexp())) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public String getName() {
                return NAME;
            }

            @Override
            public String getConfig() {
                return config.toString();
            }
        };
    }


    @Value
    public static class Config {
        String name;
        String regexp;

    }
}

package com.eleven.gateway.core.predicate;

import cn.hutool.json.JSONUtil;
import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import com.eleven.gateway.core.utils.ConfigMap;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CookieRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "cookie";

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
                    List<HttpCookie> cookies = exchange.getRequest().getCookies().get(patten.name);
                    if (cookies == null) {
                        return false;
                    }
                    for (HttpCookie cookie : cookies) {
                        if (cookie.getValue().matches(patten.regexp)) {
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
                return configString;
            }
        };
    }

    @Getter
    @Setter
    @Value
    public static class Config {
        String name;
        String regexp;
    }
}

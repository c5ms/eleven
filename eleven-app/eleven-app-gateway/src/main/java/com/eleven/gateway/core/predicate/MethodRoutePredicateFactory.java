package com.eleven.gateway.core.predicate;

import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class MethodRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "method";

    public String getName() {
        return NAME;
    }

    @Override
    public RoutePredicate apply(String configString) {
        Set<String> methods = new HashSet<>();
        for (String method : configString.split(",")) {
            method = StringUtils.trim(method);
            if (StringUtils.isNotBlank(method)) {
                methods.add(method.toUpperCase());
            }
        }
        return new RoutePredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                return methods.contains(exchange.getRequest().getMethod().name().toUpperCase(Locale.ROOT));
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

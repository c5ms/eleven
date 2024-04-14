package com.eleven.gateway.core.predicate;

import com.eleven.gateway.core.GatewayServerUtil;
import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

@Component
public class HostRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "host";
    private static final PathMatcher pathMatcher = new AntPathMatcher(".");

    public String getName() {
        return NAME;
    }

    @Override
    public RoutePredicate apply(String configString) {
        return new RoutePredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                String host = exchange.getRequest().getHeaders().getFirst("Host");
                if (ObjectUtils.isEmpty(host)) {
                    return false;
                }
                String match = null;
                if (pathMatcher.match(configString, host)) {
                    match = host;
                }
                if (match != null) {
                    Map<String, String> variables = pathMatcher.extractUriTemplateVariables(match, host);
                    GatewayServerUtil.putUriTemplateVariables(exchange, variables);
                    return true;
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


}

package com.eleven.gateway.core.predicate;

import com.eleven.gateway.core.GatewayConstants;
import com.eleven.gateway.core.GatewayServerUtil;
import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.net.URI;

@Component
public class PathRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "path";
    private final PathPatternParser pathPatternParser = new PathPatternParser();

    public String getName() {
        return NAME;
    }

    @Override
    public RoutePredicate apply(String configString) {
        PathPattern pathPattern = this.pathPatternParser.parse(configString);
        return new RoutePredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                URI uri = exchange.getRequest().getURI();
                PathContainer pathCon = PathContainer.parsePath(uri.getPath());
                if (pathPattern.matches(pathCon)) {
                    PathContainer pathWithinPattern = pathPattern.extractPathWithinPattern(pathCon);
                    exchange.getAttributes().put(GatewayConstants.GATEWAY_PREDICATE_MATCHED_PATH_ATTR, pathWithinPattern.value());
                    PathPattern.PathMatchInfo pathMatchInfo = pathPattern.matchAndExtract(pathCon);
                    if (null != pathMatchInfo) {
                        GatewayServerUtil.putUriTemplateVariables(exchange, pathMatchInfo.getUriVariables());
                    }
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

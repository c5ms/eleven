package com.eleven.gateway.core.predicate;

import com.eleven.gateway.core.RoutePredicate;
import com.eleven.gateway.core.RoutePredicateFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashSet;
import java.util.Set;

@Component
public class ContentTypeRoutePredicateFactory implements RoutePredicateFactory {
    private static final String NAME = "contentType";

    public String getName() {
        return NAME;
    }

    @Override
    public RoutePredicate apply(String configString) {
        Set<MediaType> types = new HashSet<>();
        for (String type : configString.split(",")) {
            type = StringUtils.trim(type);
            if (StringUtils.isNotBlank(type)) {
                types.add(MediaType.parseMediaType(type.toUpperCase()));
            }
        }
        return new RoutePredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                for (MediaType type : types) {
                    if (type.isCompatibleWith(exchange.getRequest().getHeaders().getContentType())) {
                        return true;
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

}

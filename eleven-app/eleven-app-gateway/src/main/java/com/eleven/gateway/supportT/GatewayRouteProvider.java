package com.eleven.gateway.supportT;

import com.eleven.core.exception.DomainErrors;
import com.eleven.core.exception.ProcessFailureException;
import com.eleven.gateway.core.*;
import com.eleven.gateway.management.exception.GatewayAdminException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayRouteProvider implements  GatewayProvider<GatewayRoute> {
    private final ApplicationContext applicationContext;

    public RoutePredicate createPredicate(String directive) {
        directive = StringUtils.trim(directive);
        String[] pair = directive.split("=");
        String name = pair[0];
        String config = pair.length > 1 ? pair[1] : "";
        Collection<RoutePredicateFactory> routePredicateFactories = applicationContext.getBeansOfType(RoutePredicateFactory.class).values();
        for (RoutePredicateFactory factory : routePredicateFactories) {
            if (StringUtils.equalsIgnoreCase(factory.getName(), name)) {
                return factory.apply(config);
            }
        }
        throw GatewayAdminException.of("路由规则解析失败,没有找到[" + name + "]");
    }

    public RouteFilter createFilter(String directive) {
        directive = StringUtils.trim(directive);
        String name = directive;
        String config = "";
        if (directive.contains("=")) {
            name = directive.substring(0, directive.indexOf("="));
            config = directive.substring(directive.indexOf("=") + 1);
        }
        Collection<RouteFilterFactory> routeFilterFactories = applicationContext.getBeansOfType(RouteFilterFactory.class).values();
        for (RouteFilterFactory factory : routeFilterFactories) {
            if (StringUtils.equalsIgnoreCase(factory.getName(), name)) {
                return factory.apply(config);
            }
        }
        throw GatewayAdminException.of("路由过滤器解析失败,没有找到[" + name + "]");
    }

    @Override
    public Collection<GatewayRoute> getInstances() {
        GatewayRoute route= GatewayRoute.builder()
            .id("test-1")
            .name("test1")
            .routeMode(GatewayRouteMode.proxy)
            .predicates(List.of(createPredicate("host=*")))
            .upstream(new GatewayRouteUpstream(new ArrayList<>(List.of("https://beautifulsoup.cn/"))))
            .build();
        return List.of(route );
    }


}

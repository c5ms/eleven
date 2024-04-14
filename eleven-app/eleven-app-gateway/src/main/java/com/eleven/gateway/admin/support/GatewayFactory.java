package com.eleven.gateway.admin.support;

import cn.hutool.extra.spring.SpringUtil;
import com.cnetong.common.cluster.MetadataManager;
import com.cnetong.common.web.ProcessRejectException;
import com.eleven.gateway.admin.domain.entity.*;
import com.eleven.gateway.admin.domain.repository.GateResourceRepository;
import com.eleven.gateway.core.*;
import com.eleven.gateway.core.predicate.ContentTypeRoutePredicateFactory;
import com.eleven.gateway.core.predicate.HostRoutePredicateFactory;
import com.eleven.gateway.core.predicate.MethodRoutePredicateFactory;
import com.eleven.gateway.core.predicate.PathRoutePredicateFactory;
import com.cnetong.security.domain.Principal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GatewayFactory {
    private final MetadataManager metadataManager;
    private final ApplicationContext applicationContext;
    private final GateResourceRepository gateResourceRepository;

    public String createApiId(String id) {
        return id + "@inner.api";
    }

    public String createRouteId(String id) {
        return id + "@inner.route";
    }

    public String createStackId(String id) {
        return id + "@inner.server";
    }

    public String createServiceId(String id) {
        return id + "@inner.service";
    }

    public String createResourceId(String id) {
        return id + "@inner.resource";
    }

    public GatewayResource createResource(GateResource gateResource) {
        return new DefaultGatewayResource(createResourceId(gateResource.getId()), gateResource.getId(), gateResourceRepository, metadataManager);
    }

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
        throw ProcessRejectException.of("路由规则解析失败,没有找到[" + name + "]");
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
        throw ProcessRejectException.of("路由过滤器解析失败,没有找到[" + name + "]");
    }

    public GatewayRoute createRoute(GateRoute definition) {

        var filters = definition.getFilters().stream()
            .map(StringUtils::trim)
            .filter(StringUtils::isNotBlank)
            .map(this::createFilter)
            .collect(Collectors.toList());

        var predicates = definition.getPredicates().stream()
            .map(StringUtils::trim)
            .filter(StringUtils::isNotBlank)
            .map(this::createPredicate)
            .collect(Collectors.toList());

        //  绑定路径
        if (StringUtils.isNotBlank(definition.getPath())) {
            var predicate = SpringUtil.getBean(PathRoutePredicateFactory.class).apply(definition.getPath());
            predicates.add(predicate);
        }
        //  绑定域名
        if (StringUtils.isNotBlank(definition.getHost())) {
            var predicate = SpringUtil.getBean(HostRoutePredicateFactory.class).apply(definition.getHost());
            predicates.add(predicate);
        }
        //  绑定方法
        if (StringUtils.isNotBlank(definition.getMethod())) {
            var predicate = SpringUtil.getBean(MethodRoutePredicateFactory.class).apply(definition.getMethod());
            predicates.add(predicate);
        }
        //  绑定类型
        if (StringUtils.isNotBlank(definition.getContentType())) {
            var predicate = SpringUtil.getBean(ContentTypeRoutePredicateFactory.class).apply(definition.getContentType());
            predicates.add(predicate);
        }

        // 上游服务
        var upstream = new GatewayRouteUpstream(new ArrayList<>(definition.getUris()));

        // 静态响应
        var resource = GatewayRouteResource.builder()
            .id(definition.getResourceId())
            .path(definition.getResourcePath())
            .build();
        if (StringUtils.isBlank(definition.getResourceId())) {
            resource = null;
        }

        // 服务
        var service = GatewayRouteService.builder()
            .id(definition.getServiceId())
            .path(definition.getServicePath())
            .build();
        if (StringUtils.isBlank(definition.getServiceId())) {
            service = null;
        }

        return GatewayRoute.builder()
            .id(createRouteId(definition.getId()))
            .service(service)
            .name(definition.getName())
            .proxyMode(definition.getProxyMode())
            .upstream(upstream)
            .secured(false)
            .routeMode(GatewayRouteMode.proxy)
            .filters(filters)
            .predicates(predicates)
            .resource(resource)
            .build();
    }

    public GatewayService createService(GateService definition) {
        return DefaultGatewayService.builder()
            .id(createServiceId(definition.getId()))
            .name(definition.getName())
            .filters(
                definition.getFilters().stream()
                    .map(StringUtils::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(this::createFilter)
                    .collect(Collectors.toList())
            )
            .upstream(new GatewayRouteUpstream(
                new ArrayList<>(definition.getInstances())
            ))
            .build();
    }

    public GatewayStack createStack(GateStack gateStack) {
        var filters = gateStack.getFilters().stream()
            .map(StringUtils::trim)
            .filter(StringUtils::isNotBlank)
            .map(this::createFilter)
            .collect(Collectors.toList());

        var hostPredicates = new ArrayList<RoutePredicate>();
        if (StringUtils.isNotBlank(gateStack.getHost()) && !StringUtils.equals(StringUtils.trim(gateStack.getHost()), "*")) {
            hostPredicates.add(SpringUtil.getBean(HostRoutePredicateFactory.class).apply(gateStack.getHost()));
        }

        // 服务下的路由
        var routes = gateStack.getGateRoutes()
            .stream()
            .filter(AbstractPublishableDomain::isPublished)
            .map(this::createRoute)
            .peek(gatewayRoute -> gatewayRoute.getFilters().addAll(0, filters))
            .collect(Collectors.toList());

        return GatewayStack.builder()
            .id(createStackId(gateStack.getId()))
            .name(gateStack.getName())
            .predicates(hostPredicates)
            .routes(routes)
            .build();


    }

    public GatewayApp createApp(GateApp gateApp) {
        var app = GatewayApp.builder()
            .id(gateApp.getAppId())
//                .token(gateApp.getToken().getToken())
            .build();
        gateApp.getApis().forEach(s -> app.grantRoute(createApiId(s)));
        return app;
    }

    public GatewayToken createToken(GateApp gateApp) {
        return GatewayToken.builder()
            .value(gateApp.getToken().getToken())
            .issuer(SpringUtil.getApplicationName())
            .expireAt(gateApp.getToken().getExpireAt())
            .principal(new Principal(GateApp.GATE_APP_TYPE, gateApp.getAppId()))
            .build();
    }

    public GatewayRoute createRoute(GateApi definition) {

        var predicates = new ArrayList<RoutePredicate>();

        //  绑定路径
        if (StringUtils.isNotBlank(definition.getPath())) {
            var predicate = SpringUtil.getBean(PathRoutePredicateFactory.class).apply(definition.getPath());
            predicates.add(predicate);
        }
        //  绑定域名
        if (StringUtils.isNotBlank(definition.getHost())) {
            var predicate = SpringUtil.getBean(HostRoutePredicateFactory.class).apply(definition.getHost());
            predicates.add(predicate);
        }
        //  绑定方法
        if (StringUtils.isNotBlank(definition.getMethod())) {
            var predicate = SpringUtil.getBean(MethodRoutePredicateFactory.class).apply(definition.getMethod());
            predicates.add(predicate);
        }

        // 服务
        var service = Optional.ofNullable(definition.getServiceId())
            .map(s -> GatewayRouteService.builder()
                .id(definition.getServiceId())
                .path(definition.getServicePath())
                .build())
            .orElse(null);

        // 上游服务
        var upstream = new GatewayRouteUpstream(new ArrayList<>(definition.getUris()));

        return GatewayRoute.builder()
            .id(createApiId(definition.getId()))
            .group(definition.getGroup())
            .name(definition.getName())
            .proxyMode(definition.getProxyMode())
            .secured(definition.getSecure())
            .routeMode(GatewayRouteMode.api)
            .service(service)
            .upstream(upstream)
            .predicates(predicates)
            .build()
            ;

    }
}

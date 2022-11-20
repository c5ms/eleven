package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

@Getter
public class GatewayRoute implements Serializable {

    private final String id;
    private final String group;
    private final String name;
    private final Map<String, Object> metadata = new HashMap<>();

    private final List<RouteFilter> filters;
    private final List<RoutePredicate> predicates;
    private final GatewayRouteRestrictions restrictions;
    private final boolean secured;
    private final Duration timeout;
    private final GatewayRouteMode routeMode;
    private final GatewayRouteProxyMode proxyMode;

    private final GatewayRouteService service;
    private final GatewayRouteResource resource;
    private final GatewayRouteUpstream upstream;

    @Builder
    public GatewayRoute(String id,
                        String group,
                        String name,
                        GatewayRouteUpstream upstream,
                        Duration timeout,
                        GatewayRouteService service,
                        GatewayRouteResource resource,
                        GatewayRouteProxyMode proxyMode,
                        GatewayRouteRestrictions restrictions,
                        List<RoutePredicate> predicates,
                        List<RouteFilter> filters,
                        Boolean secured,
                        GatewayRouteMode routeMode) {
        Assert.hasText(id, "id must have a value");
        Assert.hasText(name, "name must have a value");
        this.id = id;
        this.group = group;
        this.name = name;
        this.service = service;
        this.resource = resource;
        this.restrictions = restrictions;
        this.routeMode = Optional.ofNullable(routeMode).orElse(GatewayRouteMode.proxy);
        this.upstream = Optional.ofNullable(upstream).orElse(new GatewayRouteUpstream(new ArrayList<>()));
        this.secured = Optional.ofNullable(secured).orElse(false);
        this.proxyMode = Optional.ofNullable(proxyMode).orElse(GatewayRouteProxyMode.staticProxy);
        this.timeout = Optional.ofNullable(timeout).orElseGet(() -> Duration.ofSeconds(60));
        this.predicates = Optional.ofNullable(predicates).orElseGet(ArrayList::new);
        this.filters = Optional.ofNullable(filters).orElseGet(ArrayList::new);
    }


    /**
     * 匹配一次请求
     *
     * @param exchange 请求
     * @return true 可处理
     */
    public boolean match(ServerWebExchange exchange) {
        return getPredicates()
            .stream()
            .allMatch(routePredicate -> routePredicate.test(exchange));
    }

    /**
     * 校验请求，返回 ok 表示校验通过
     *
     * @param exchange 请求
     * @return 结果
     */
    public HttpStatus validate(ServerWebExchange exchange) {
        var gatewayRoute = this;
        // 校验
        if (null != gatewayRoute.getRestrictions()) {
            if (!gatewayRoute.getRestrictions().getMethods().isEmpty()) {
                if (!gatewayRoute.getRestrictions().getMethods().contains(exchange.getRequest().getMethod().name().toLowerCase())) {
                    return HttpStatus.METHOD_NOT_ALLOWED;
                }
            }
            if (!gatewayRoute.getRestrictions().getContentTypes().isEmpty()) {
                MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
                if (null == mediaType) {
                    return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
                }
                boolean pass = false;
                for (MediaType contentType : gatewayRoute.getRestrictions().getContentTypes()) {
                    if (mediaType.isCompatibleWith(contentType)) {
                        pass = true;
                        break;
                    }
                }
                if (!pass) {
                    return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
                }
            }
        }
        return HttpStatus.OK;
    }

    public Optional<URI> route(ServerWebExchange exchange) {
        GatewayService gatewayService = exchange.getAttribute(GatewayConstants.GATEWAY_SERVICE_ATTR);
        if (null != gatewayService) {
            String serviceUri = gatewayService.selectUri();
            var uri = URI.create(serviceUri + "/").resolve(service.getPath());
            exchange.getResponse().getHeaders().set(GatewayConstants.GATEWAY_HEADER_GATEWAY_SERVICE, getService().getId());
            return Optional.of(mergeUrl(exchange, uri));
        }
        if (null != upstream) {
            var uri = upstream.selectUri();
            if (null != uri) {
                return Optional.of(mergeUrl(exchange, URI.create(uri)));
            }
        }
        return Optional.empty();
    }

    public Optional<GatewayContent> matchResource(ServerWebExchange exchange) {
        if (null == getResource()) {
            return Optional.empty();
        }
        var filePath = "/";
        String matchPath = exchange.getAttributeOrDefault(GatewayConstants.GATEWAY_PREDICATE_MATCHED_PATH_ATTR, "/");
        GatewayResource resourceInstance = exchange.getAttribute(GatewayConstants.GATEWAY_RESOURCE_ATTR);
        if (null == resourceInstance) {
            return Optional.empty();
        }
        if (getProxyMode() == GatewayRouteProxyMode.dynamicProxy) {
            filePath = matchPath;
        }
        if (getProxyMode() == GatewayRouteProxyMode.staticProxy) {
            filePath = exchange.getRequest().getPath().value();
        }
        filePath = Paths.get(getResource().getPath(), filePath).toString();
        var resOpt = resourceInstance.load(filePath);
        if (resOpt.isPresent()) {
            exchange.getResponse().getHeaders().set(GatewayConstants.GATEWAY_HEADER_GATEWAY_RESOURCE, resourceInstance.getId());
        }
        return resOpt;
    }

    private URI mergeUrl(ServerWebExchange exchange, URI routeUri) {
        URI requestUri = exchange.getRequest().getURI();
        String matchPath = exchange.getAttributeOrDefault(GatewayConstants.GATEWAY_PREDICATE_MATCHED_PATH_ATTR, "/");
        if (getProxyMode() == GatewayRouteProxyMode.dynamicProxy) {
            return UriComponentsBuilder.fromUri(routeUri)
                .path(!StringUtils.hasLength(matchPath) ? "" : "/" + matchPath)
                .queryParams(exchange.getRequest().getQueryParams())
                .fragment(requestUri.getFragment())
                .build(false)
                .toUri();
        }
        if (getProxyMode() == GatewayRouteProxyMode.staticProxy) {
            boolean encoded = (requestUri.getRawQuery() != null && requestUri.getRawQuery().contains("%")) || (requestUri.getPath() != null && requestUri.getRawPath().contains("%"));
            return UriComponentsBuilder.fromUri(routeUri)
                .path(requestUri.getRawPath())
                .query(requestUri.getRawQuery())
                .fragment(requestUri.getFragment())
                .build(encoded)
                .toUri();
        }
        return routeUri;
    }


}

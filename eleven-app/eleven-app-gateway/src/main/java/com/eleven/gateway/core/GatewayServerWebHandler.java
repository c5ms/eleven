//package com.eleven.gateway.core;
//
//import com.eleven.gateway.core.filter.global.*;
//import io.micrometer.tracing.Tracer;
//import io.micrometer.tracing.annotation.NewSpan;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
//import org.springframework.web.reactive.socket.client.WebSocketClient;
//import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
//import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebHandler;
//import org.springframework.web.util.ForwardedHeaderUtils;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.resources.ConnectionProvider;
//
//import java.net.InetSocketAddress;
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class GatewayServerWebHandler implements WebHandler {
//    private final Tracer tracer;
//
//    private final GatewayObserver gatewayObserver;
//    private final GatewayRouteBroker routeBroker;
//    private final GatewayProvider<GatewayStack> stackProvider;
//    private final GatewayProvider<GatewayService> serviceProvider;
//    private final GatewayProvider<GatewayResource> resourceProvider;
//
//    private final NettyProxyRouteFilter nettyProxyRouteFilter;
//    private final NettyWriteRouteFilter nettyWriteRouteFilter;
//    private final RequestUrlRouteFilter requestUrlRouteFilter;
//    private final WebsocketRoutingFilter websocketRoutingFilter;
//    private final StaticResourceRouteFilter staticResourceRouteFilter;
//
//    private HttpClient httpClient;
//    private WebSocketClient webSocketClient;
//    private HandshakeWebSocketService webSocketService;
//    private GatewayConfig config;
//
//    void start(GatewayConfig config) {
//        this.config = config;
//        this.httpClient = HttpClient.create(ConnectionProvider.builder("gateway.http.client")
//            .maxConnections(config.getHttpClient().getMaxConnections())
//            .maxIdleTime(Duration.of(6, ChronoUnit.SECONDS))
//            .build());
//        this.webSocketClient = new ReactorNettyWebSocketClient(httpClient);
//        this.webSocketService = new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
//        this.webSocketService.start();
//    }
//
//    void stop() {
//        this.webSocketService.stop();
//    }
//
//    @NonNull
//    @NewSpan
//    public Mono<Void> handle(@NonNull ServerWebExchange exchange) {
//        exchange.getResponse().getHeaders().set(GatewayConstants.GATEWAY_HEADER_GATEWAY_SERVER, config.getServer().getName());
//
//        return Mono.just(exchange)
//            .doOnNext(exchange1 -> {
//                var span = Optional.ofNullable(tracer.currentSpan()).orElseGet(() -> tracer.nextSpan().start());
//                span.name(String.format("request %s", exchange.getRequest().getPath()));
//            })
//            .doOnNext(gatewayObserver::onRequest)
//            .map(this::toContext)
//            .flatMap(this::doRoute)
//            .then();
//    }
//
//    private RouteContext toContext(ServerWebExchange exchange) {
//
//        // 动态代理增加 forward 信息
//        var span = tracer.nextSpan();
//        exchange.getResponse().getHeaders().add(GatewayConstants.GATEWAY_HEADER_TRACE_ID, span.context().traceId());
//        final var request = exchange.getRequest().mutate().headers(httpHeaders -> {
//            InetSocketAddress address = ForwardedHeaderUtils.parseForwardedFor(exchange.getRequest().getURI(), exchange.getRequest().getHeaders(), exchange.getRequest().getRemoteAddress());
//            String remoteHost = (address != null ? address.getHostString() : Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString());
//            httpHeaders.add(GatewayConstants.GATEWAY_HEADER_FORWARDED_FOR, remoteHost);
//            httpHeaders.add("x-b3-spanid", span.context().spanId());
//            httpHeaders.add("x-b3-traceid", span.context().traceId());
//            httpHeaders.add("x-b3-parentspanid", span.context().parentId());
//        }).build();
//
//
//        final var context = new RouteContext(
//            exchange.mutate().request(request).build()
//        );
//
//        // 查找路由
//        this.lookupRoute(context).ifPresent(context::setRoute);
//        if (null == context.getRoute()) {
//            return context;
//        }
//
//        // 查找静态资源
//        Optional.ofNullable(context.getRoute().getResource())
//            .flatMap(this::lookupResource)
//            .ifPresent(context::setResource);
//
//        // 查找服务
//        Optional.ofNullable(context.getRoute().getService())
//            .flatMap(this::lookupService)
//            .ifPresent(context::setService);
//
//        //处理路由携带的过滤器
//        var filters = new ArrayList<>(context.getRoute().getFilters());
//
//        // 添加服务处理器
//        Optional.ofNullable(context.getService())
//            .map(GatewayService::getFilters)
//            .ifPresent(filters::addAll);
//
//        filters.add(staticResourceRouteFilter);
//        filters.add(requestUrlRouteFilter);
//        filters.add(websocketRoutingFilter);
//        filters.add(nettyProxyRouteFilter);
//        filters.add(nettyWriteRouteFilter);
//
//        context.setFilterChain(new RouteFilterChain(filters));
//
//        // 路由参数
//        exchange.getAttributes().put(GatewayConstants.GATEWAY_HTTP_CLIENT, httpClient);
//        exchange.getAttributes().put(GatewayConstants.GATEWAY_WEB_SOCKET_CLIENT, webSocketClient);
//        exchange.getAttributes().put(GatewayConstants.GATEWAY_WEB_SOCKET_SERVICE, webSocketService);
//
//        if (null != context.getRoute()) {
//            exchange.getAttributes().put(GatewayConstants.GATEWAY_ROUTE_ATTR, context.getRoute());
//        }
//        if (null != context.getService()) {
//            exchange.getAttributes().put(GatewayConstants.GATEWAY_SERVICE_ATTR, context.getService());
//        }
//        if (null != context.getResource()) {
//            exchange.getAttributes().put(GatewayConstants.GATEWAY_RESOURCE_ATTR, context.getResource());
//        }
//
//        return context;
//    }
//
//    @NotNull
//    private Mono<Void> doRoute(RouteContext context) {
//        var exchange = context.getExchange();
//
//        var route = context.getRoute();
//
//        if (null == route) {
//            return GatewayServerUtil.response(exchange, GatewayError.NO_ROUTE);
//        }
//
//        gatewayObserver.onRoute(exchange, route);
//        exchange.getResponse().getHeaders().set(GatewayConstants.GATEWAY_HEADER_GATEWAY_ROUTE, route.getId());
//
//        // 路由校验
//        var validateResult = route.validate(exchange);
//        if (validateResult != HttpStatus.OK) {
//            return GatewayServerUtil.response(exchange, validateResult);
//        }
//
//        return context.getFilterChain()
//            .filter(exchange)
//            .doOnSuccess(unused -> whenSuccess(context))
//            .onErrorResume(throwable -> whenException(context, throwable))
//            .doFinally(signalType -> whenAccess(context));
//    }
//
//    private void whenAccess(RouteContext context) {
//        // nothing ?
//    }
//
//    @NotNull
//    private Mono<Void> whenException(RouteContext context, Throwable throwable) {
//        gatewayObserver.onError(context.getExchange(), context.getRoute(), throwable);
//        if (throwable instanceof TimeoutException) {
//            return GatewayServerUtil.response(context.getExchange(), HttpStatus.GATEWAY_TIMEOUT);
//        }
//        return GatewayServerUtil.response(context.getExchange(), GatewayError.PROCESS_FAILURE);
//    }
//
//    private void whenSuccess(RouteContext context) {
//        var status = context.getExchange().getResponse().getStatusCode();
//        if (null != status) {
//            if (status.isError()) {
//                gatewayObserver.onRouteFailure(context.getExchange(), context.getRoute());
//            } else {
//                gatewayObserver.onRouteSuccess(context.getExchange(), context.getRoute());
//            }
//        }
//    }
//
//    private Optional<GatewayRoute> lookupRoute(RouteContext context) {
//        var exchange = context.getExchange();
//
//        // find from global routes
//        var globalRoute = routeBroker.getRoutes()
//            .stream()
//            .filter(gatewayRoute -> gatewayRoute.match(exchange))
//            .findFirst();
//        if (globalRoute.isPresent()) {
//            return globalRoute;
//        }
//
//        // find from stacks
//        for (GatewayStack stack : stackProvider.getInstances()) {
//            if (stack.match(exchange)) {
//                for (GatewayRoute route : stack.getRoutes()) {
//                    if (route.match(exchange)) {
//                        return Optional.of(route);
//                    }
//                }
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    private Optional<GatewayService> lookupService(GatewayRouteService service) {
//        return serviceProvider.getInstances()
//            .stream()
//            // todo can be replaced with map#get e.g.  #lookup(by id )
//            .filter(instance -> instance.getId().equals(service.getId()))
//            .findFirst();
//    }
//
//    private Optional<GatewayResource> lookupResource(GatewayRouteResource resource) {
//        return resourceProvider.getInstances()
//            .stream()
//            // todo can be replaced with map#get e.g.  #lookup(by id )
//            .filter(instance -> instance.getId().equals(resource.getId()))
//            .findFirst();
//    }
//
//
//
//    @Getter
//    @Setter
//    @Accessors(chain = true)
//    public static class RouteContext {
//        private ServerWebExchange exchange;
//
//        private GatewayRoute route;
//        private GatewayService service;
//        private GatewayResource resource;
//        private RouteFilterChain filterChain;
//
//        public RouteContext(ServerWebExchange exchange) {
//            this.exchange = exchange;
//        }
//    }
//
//}

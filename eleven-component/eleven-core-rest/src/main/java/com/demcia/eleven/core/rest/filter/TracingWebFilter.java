package com.demcia.eleven.core.rest.filter;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order
@Component
public class TracingWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain webFilterChain) {
        exchange.getResponse().getHeaders().set("X-Service-Provider", SpringUtil.getApplicationName());
        return webFilterChain.filter(exchange);
    }
}
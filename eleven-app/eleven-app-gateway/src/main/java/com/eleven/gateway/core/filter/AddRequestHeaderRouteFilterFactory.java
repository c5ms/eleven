/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eleven.gateway.core.filter;

import cn.hutool.json.JSONUtil;
import com.eleven.gateway.core.GatewayServerUtil;
import com.eleven.gateway.core.RouteFilter;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteFilterFactory;
import com.eleven.gateway.core.utils.ConfigMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class AddRequestHeaderRouteFilterFactory implements RouteFilterFactory {
    private static final String NAME = "addRequestHeader";

    public String getName() {
        return NAME;
    }

    @Override
    public RouteFilter apply(String configString) {
        ConfigMap config = JSONUtil.toBean(configString, ConfigMap.class);

        return new RouteFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
                HttpHeaders headers = new HttpHeaders();
                for (Map.Entry<String, String> entry : config.entrySet()) {
                    String value = GatewayServerUtil.expand(exchange, entry.getValue());
                    headers.add(entry.getKey(), value);
                }
                ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .build();
                return chain.filter(exchange.mutate().request(request).build());
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

package com.eleven.gateway.core.filter;

import cn.hutool.json.JSONUtil;
import com.eleven.gateway.core.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RewritePathRouteFilterFactory implements RouteFilterFactory {
    private static final String NAME = "rewritePath";

    public String getName() {
        return NAME;
    }

    @Override
    public RouteFilter apply(String configString) {
        Config config = JSONUtil.toBean(configString, Config.class);
        String replacement = config.replacement.replace("$\\", "$");
        return new RouteFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
                ServerHttpRequest req = exchange.getRequest();
                GatewayServerUtil.addOriginalRequestUrl(exchange, req.getURI());
                String path = req.getURI().getRawPath();
                String newPath = path.replaceAll(config.regexp, replacement);

                ServerHttpRequest request = req.mutate().path(newPath).build();

                exchange.getAttributes().put(GatewayConstants.GATEWAY_REQUEST_URL_ATTR, request.getURI());

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

    public static class Config {

        private String regexp;

        private String replacement;

        public String getRegexp() {
            return regexp;
        }

        public Config setRegexp(String regexp) {
            Assert.hasText(regexp, "regexp must have a value");
            this.regexp = regexp;
            return this;
        }

        public String getReplacement() {
            return replacement;
        }

        public Config setReplacement(String replacement) {
            Assert.notNull(replacement, "replacement must not be null");
            this.replacement = replacement;
            return this;
        }


        @Override
        public String toString() {
            return "{" +
                    "regexp='" + regexp + '\'' +
                    ", replacement='" + replacement + '\'' +
                    '}';
        }
    }

}

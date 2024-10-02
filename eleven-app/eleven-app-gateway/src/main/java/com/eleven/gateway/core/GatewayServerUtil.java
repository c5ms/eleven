package com.eleven.gateway.core;

import com.eleven.gateway.core.utils.GatewayNettyDataBufferUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Slf4j
public class GatewayServerUtil {

    public static void setAlreadyRouted(ServerWebExchange exchange) {
        exchange.getAttributes().put(GatewayConstants.GATEWAY_ALREADY_ROUTED_ATTR, true);
    }

    public static boolean isAlreadyRouted(ServerWebExchange exchange) {
        return exchange.getAttributeOrDefault(GatewayConstants.GATEWAY_ALREADY_ROUTED_ATTR, false);
    }

    public static String expand(ServerWebExchange exchange, String template) {
        Assert.notNull(exchange, "exchange may not be null");
        Assert.notNull(template, "template may not be null");

        if (template.indexOf('{') == -1) { // short circuit
            return template;
        }

        Map<String, Object> variables = getUriTemplateVariables(exchange);
        return UriComponentsBuilder.fromPath(template).build().expand(variables).getPath();
    }


    public static Map<String, Object> getUriTemplateVariables(ServerWebExchange exchange) {
        return exchange.getAttributeOrDefault(GatewayConstants.URI_TEMPLATE_VARIABLES_ATTRIBUTE, new HashMap<>());
    }


    @SuppressWarnings("unchecked")
    public static void putUriTemplateVariables(ServerWebExchange exchange, Map<String, String> uriVariables) {
        if (exchange.getAttributes().containsKey(GatewayConstants.URI_TEMPLATE_VARIABLES_ATTRIBUTE)) {
            Map<String, Object> existingVariables = (Map<String, Object>) exchange.getAttributes().get(GatewayConstants.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            HashMap<String, Object> newVariables = new HashMap<>();
            newVariables.putAll(existingVariables);
            newVariables.putAll(uriVariables);
            exchange.getAttributes().put(GatewayConstants.URI_TEMPLATE_VARIABLES_ATTRIBUTE, newVariables);
        } else {
            exchange.getAttributes().put(GatewayConstants.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriVariables);
        }
    }

    public static boolean containsEncodedParts(URI uri) {
        boolean encoded = (uri.getRawQuery() != null && uri.getRawQuery().contains("%")) || (uri.getRawPath() != null && uri.getRawPath().contains("%"));

        // Verify if it is really fully encoded. Treat partial encoded as unencoded.
        if (encoded) {
            try {
                UriComponentsBuilder.fromUri(uri).build(true);
                return true;
            } catch (IllegalArgumentException e) {
                if (log.isTraceEnabled()) {
                    log.trace("Error in containsEncodedParts", e);
                }
            }

            return false;
        }

        return false;
    }

    public static void addOriginalRequestUrl(ServerWebExchange exchange, URI url) {
        exchange.getAttributes().computeIfAbsent(GatewayConstants.GATEWAY_ORIGINAL_REQUEST_URL_ATTR, s -> new LinkedHashSet<>());
        LinkedHashSet<URI> uris = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        uris.add(url);
    }

    public static Mono<Void> response(ServerWebExchange exchange, HttpStatus state) {
        setAlreadyRouted(exchange);
        exchange.getResponse().setStatusCode(state);
        return Mono.defer(() -> response(exchange, state.getReasonPhrase()));
    }

    public static Mono<Void> response(ServerWebExchange exchange, String body) {
        setAlreadyRouted(exchange);
        return Mono.defer(() -> exchange.getResponse().writeWith(Mono.just(GatewayNettyDataBufferUtils.from(body))));
    }

    public static Mono<Void> response(ServerWebExchange exchange, byte[] body) {
        setAlreadyRouted(exchange);
        return Mono.defer(() -> exchange.getResponse().writeWith(Mono.just(GatewayNettyDataBufferUtils.from(body))));
    }

    public static Mono<Void> response(ServerWebExchange exchange, GatewayError error) {
        setAlreadyRouted(exchange);
        exchange.getResponse().setStatusCode(error.getStatus());
        exchange.getResponse().getHeaders().set(GatewayConstants.GATEWAY_HEADER_GATEWAY_ERROR, error.getMessage());
        return Mono.empty();
    }

}

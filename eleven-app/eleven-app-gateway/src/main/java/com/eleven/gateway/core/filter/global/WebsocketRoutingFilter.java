package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.GatewayConstants;
import com.eleven.gateway.core.GatewayServerUtil;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteGlobalFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketRoutingFilter implements RouteGlobalFilter {

    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";


    /* for testing */
    static String convertHttpToWs(String scheme) {
        scheme = scheme.toLowerCase();
        return "http".equals(scheme) ? "ws" : "https".equals(scheme) ? "wss" : scheme;
    }

    static void changeSchemeIfIsWebSocketUpgrade(ServerWebExchange exchange) {
        // Check the Upgrade
        URI requestUrl = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_REQUEST_URL_ATTR);
        String scheme = requestUrl.getScheme().toLowerCase();
        String upgrade = exchange.getRequest().getHeaders().getUpgrade();
        // change the scheme if the socket client send a "http" or "https"
        if ("WebSocket".equalsIgnoreCase(upgrade) && ("http".equals(scheme) || "https".equals(scheme))) {
            String wsScheme = convertHttpToWs(scheme);
            boolean encoded = GatewayServerUtil.containsEncodedParts(requestUrl);
            URI wsRequestUrl = UriComponentsBuilder.fromUri(requestUrl).scheme(wsScheme).build(encoded).toUri();
            exchange.getAttributes().put(GatewayConstants.GATEWAY_REQUEST_URL_ATTR, wsRequestUrl);
            if (log.isTraceEnabled()) {
                log.trace("changeSchemeTo:[" + wsRequestUrl + "]");
            }
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        changeSchemeIfIsWebSocketUpgrade(exchange);

        URI requestUrl = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_REQUEST_URL_ATTR);
        String scheme = requestUrl.getScheme();

        if (GatewayServerUtil.isAlreadyRouted(exchange) || (!"ws".equals(scheme) && !"wss".equals(scheme))) {
            return chain.filter(exchange);
        }
        GatewayServerUtil.setAlreadyRouted(exchange);

        HttpHeaders headers = exchange.getRequest().getHeaders();
//		HttpHeaders filtered = filterRequest(getHeadersFilters(), exchange);

        List<String> protocols = getProtocols(headers);

        WebSocketClient webSocketClient = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_WEB_SOCKET_CLIENT);
        WebSocketService webSocketService = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_WEB_SOCKET_SERVICE);

        return webSocketService.handleRequest(exchange,
                new ProxyWebSocketHandler(requestUrl, webSocketClient, headers, protocols));
    }

    /* for testing */ List<String> getProtocols(HttpHeaders headers) {
        List<String> protocols = headers.get(SEC_WEBSOCKET_PROTOCOL);
        if (protocols != null) {
            ArrayList<String> updatedProtocols = new ArrayList<>();
            for (String protocol : protocols) {
                updatedProtocols.addAll(Arrays.asList(StringUtils.tokenizeToStringArray(protocol, ",")));
            }
            protocols = updatedProtocols;
        }
        return protocols;
    }

    private static class ProxyWebSocketHandler implements WebSocketHandler {

        private final WebSocketClient client;

        private final URI url;

        private final HttpHeaders headers;

        private final List<String> subProtocols;

        ProxyWebSocketHandler(URI url, WebSocketClient client, HttpHeaders headers, List<String> protocols) {
            this.client = client;
            this.url = url;
            this.headers = headers;
            if (protocols != null) {
                this.subProtocols = protocols;
            } else {
                this.subProtocols = Collections.emptyList();
            }
        }

        @Override
        public List<String> getSubProtocols() {
            return this.subProtocols;
        }

        @Override
        public Mono<Void> handle(WebSocketSession session) {
            // pass headers along so custom headers can be sent through
            return client.execute(url, this.headers, new WebSocketHandler() {
                @Override
                public Mono<Void> handle(WebSocketSession proxySession) {
                    Mono<Void> serverClose = proxySession.closeStatus().filter(__ -> session.isOpen())
                            .flatMap(session::close);
                    Mono<Void> proxyClose = session.closeStatus().filter(__ -> proxySession.isOpen())
                            .flatMap(proxySession::close);
                    // Use retain() for Reactor Netty
                    Mono<Void> proxySessionSend = proxySession
                            .send(session.receive().doOnNext(WebSocketMessage::retain));
                    // .log("proxySessionSend", Level.FINE);
                    Mono<Void> serverSessionSend = session
                            .send(proxySession.receive().doOnNext(WebSocketMessage::retain));
                    // .log("sessionSend", Level.FINE);
                    // Ensure closeStatus from one propagates to the other
                    Mono.when(serverClose, proxyClose).subscribe();
                    // Complete when both sessions are done
                    return Mono.zip(proxySessionSend, serverSessionSend).then();
                }

                /**
                 * Copy subProtocols so they are available downstream.
                 * @return available subProtocols.
                 */
                @Override
                public List<String> getSubProtocols() {
                    return ProxyWebSocketHandler.this.subProtocols;
                }
            });
        }

    }

}

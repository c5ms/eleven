package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyProxyRouteFilter implements RouteGlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        if (GatewayServerUtil.isAlreadyRouted(exchange)) {
            return chain.filter(exchange);
        }

        URI requestUrl = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_REQUEST_URL_ATTR);
        GatewayRoute gatewayRoute = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_ROUTE_ATTR);
        boolean preserveHost = exchange.getAttributeOrDefault(GatewayConstants.PRESERVE_HOST_HEADER_ATTRIBUTE, false);

        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = HttpMethod.valueOf(request.getMethod().name());
        String url = requestUrl.toASCIIString();
        String scheme = request.getURI().getScheme();

        // 只处理 http 和 https
        if (GatewayServerUtil.isAlreadyRouted(exchange) || (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme))) {
            return chain.filter(exchange);
        }

        GatewayServerUtil.setAlreadyRouted(exchange);

        // 复制头
        final DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
        request.getHeaders().forEach(httpHeaders::set);

        // 处理转发
        Flux<HttpClientResponse> responseFlux = getHttpClient(gatewayRoute, exchange).headers(headers -> {
            headers.add(httpHeaders);
            // Will either be set below, or later by Netty
            headers.remove(HttpHeaders.HOST);
            if (preserveHost) {
                String host = request.getHeaders().getFirst(HttpHeaders.HOST);
                headers.add(HttpHeaders.HOST, host);
            }
        }).request(method).uri(url).send((req, nettyOutbound) -> {
            if (log.isTraceEnabled()) {
                nettyOutbound.withConnection(connection -> log.trace("outbound route: " + connection.channel().id().asShortText() + ", inbound: " + exchange.getLogPrefix()));
            }
            return nettyOutbound.send(request.getBody().map(this::getByteBuf));
        }).responseConnection((res, connection) -> {
            exchange.getAttributes().put(GatewayConstants.CLIENT_RESPONSE_ATTR, res);
            exchange.getAttributes().put(GatewayConstants.CLIENT_RESPONSE_CONN_ATTR, connection);


            ServerHttpResponse response = exchange.getResponse();
            // put headers and status so filters can modify the response
            HttpHeaders headers = new HttpHeaders();
            res.responseHeaders().forEach(entry -> headers.add(entry.getKey(), entry.getValue()));

            setResponseStatus(res, response);

            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING) && headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                // It is not valid to have both the transfer-encoding header and
                // the content-length header.
                // Remove the transfer-encoding header in the response if the
                // content-length header is present.
                response.getHeaders().remove(HttpHeaders.TRANSFER_ENCODING);
            }

            response.getHeaders().addAll(headers);

            if (response.getHeaders().containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                if (response.getHeaders().get(HttpHeaders.TRANSFER_ENCODING).size() > 1) {
                    response.getHeaders().set(HttpHeaders.TRANSFER_ENCODING, response.getHeaders().getFirst(HttpHeaders.TRANSFER_ENCODING));
                }
            }

            return Mono.just(res);
        });

        Duration responseTimeout = gatewayRoute.getTimeout();
        if (responseTimeout != null) {
            responseFlux = responseFlux
                    .timeout(responseTimeout,
                            Mono.error(new TimeoutException("Response took longer than timeout: " + responseTimeout)))
                    .onErrorMap(TimeoutException.class, th -> new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, th.getMessage(), th));
        }

        return responseFlux.then(chain.filter(exchange));

    }


    private void setResponseStatus(HttpClientResponse clientResponse, ServerHttpResponse response) {
        HttpStatus status = HttpStatus.resolve(clientResponse.status().code());
        if (status != null) {
            response.setStatusCode(status);
        } else {
            while (response instanceof ServerHttpResponseDecorator) {
                response = ((ServerHttpResponseDecorator) response).getDelegate();
            }
            if (response instanceof AbstractServerHttpResponse) {
                ((AbstractServerHttpResponse) response).setRawStatusCode(clientResponse.status().code());
            } else {
                // TODO: log warning here, not throw error?
                throw new IllegalStateException("Unable to set status code " + clientResponse.status().code()
                        + " on response of type " + response.getClass().getName());
            }
        }
    }

    protected ByteBuf getByteBuf(DataBuffer dataBuffer) {
        if (dataBuffer instanceof NettyDataBuffer) {
            NettyDataBuffer buffer = (NettyDataBuffer) dataBuffer;
            return buffer.getNativeBuffer();
        }
        // MockServerHttpResponse creates these
        else if (dataBuffer instanceof DefaultDataBuffer) {
            DefaultDataBuffer buffer = (DefaultDataBuffer) dataBuffer;
            return Unpooled.wrappedBuffer(buffer.getNativeBuffer());
        }
        throw new IllegalArgumentException("Unable to handle DataBuffer of type " + dataBuffer.getClass());
    }


    protected HttpClient getHttpClient(GatewayRoute gatewayRoute, ServerWebExchange exchange) {
        HttpClient httpClient = exchange.getRequiredAttribute(GatewayConstants.GATEWAY_HTTP_CLIENT);
        Duration timeout = gatewayRoute.getTimeout();
        if (timeout != null) {
            Integer connectTimeout = Math.toIntExact(timeout.toMillis());
            return httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
        }
        return httpClient;
    }

}

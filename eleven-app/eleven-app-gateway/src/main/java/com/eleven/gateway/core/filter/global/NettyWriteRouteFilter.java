package com.eleven.gateway.core.filter.global;

import com.eleven.gateway.core.GatewayConstants;
import com.eleven.gateway.core.RouteFilterChain;
import com.eleven.gateway.core.RouteGlobalFilter;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class NettyWriteRouteFilter implements RouteGlobalFilter {

    private final List<MediaType> streamingMediaTypes = Arrays.asList(
        MediaType.TEXT_EVENT_STREAM,
        MediaType.APPLICATION_JSON,
        new MediaType("application", "grpc"),
        new MediaType("application", "grpc+protobuf"),
        new MediaType("application", "grpc+json"));


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, RouteFilterChain chain) {
        // NOTICE: nothing in "pre" filter stage as CLIENT_RESPONSE_CONN_ATTR is not added
        // until the NettyRoutingFilter is run
        // @formatter:off
		return chain.filter(exchange)
			.doOnError(throwable -> cleanup(exchange))
			.then(Mono.defer(() -> {
				Connection connection = exchange.getAttribute(GatewayConstants.CLIENT_RESPONSE_CONN_ATTR);

				if (connection == null) {
					return Mono.empty();
				}
				if (log.isTraceEnabled()) {
					log.trace("NettyWriteResponseFilter start inbound: "
						+ connection.channel().id().asShortText() + ", outbound: "
						+ exchange.getLogPrefix());
				}
				ServerHttpResponse response = exchange.getResponse();

				// TODO: needed?
				final Flux<DataBuffer> body = connection
					.inbound()
					.receive()
					.retain()
					.map(byteBuf -> wrap(byteBuf, response));

				MediaType contentType = null;
				try {
					contentType = response.getHeaders().getContentType();
				} catch (Exception e) {
					if (log.isTraceEnabled()) {
						log.trace("invalid media type", e);
					}
				}
				return (isStreamingMediaType(contentType)
					? response.writeAndFlushWith(body.map(Flux::just))
					: response.writeWith(body));
			})).doOnCancel(() -> cleanup(exchange));
		// @formatter:on
    }


    protected DataBuffer wrap(ByteBuf byteBuf, ServerHttpResponse response) {
        DataBufferFactory bufferFactory = response.bufferFactory();
        if (bufferFactory instanceof NettyDataBufferFactory) {
            NettyDataBufferFactory factory = (NettyDataBufferFactory) bufferFactory;
            return factory.wrap(byteBuf);
        }
        // MockServerHttpResponse creates these
        else if (bufferFactory instanceof DefaultDataBufferFactory) {
            DataBuffer buffer = ((DefaultDataBufferFactory) bufferFactory).allocateBuffer(byteBuf.readableBytes());
            buffer.write(byteBuf.nioBuffer());
            byteBuf.release();
            return buffer;
        }
        throw new IllegalArgumentException("Unkown DataBufferFactory type " + bufferFactory.getClass());
    }

    private void cleanup(ServerWebExchange exchange) {
        Connection connection = exchange.getAttribute(GatewayConstants.CLIENT_RESPONSE_CONN_ATTR);
        if (connection != null && connection.channel().isActive() && !connection.isPersistent()) {
            connection.dispose();
        }
    }


    // TODO: use framework if possible
    private boolean isStreamingMediaType(@Nullable MediaType contentType) {
        if (contentType != null) {
            for (MediaType streamingMediaType : streamingMediaTypes) {
                if (streamingMediaType.isCompatibleWith(contentType)) {
                    return true;
                }
            }
        }
        return false;
    }
}

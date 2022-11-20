package com.eleven.gateway.core;

import com.eleven.gateway.core.boost.Bootstrap;
import com.eleven.gateway.core.support.AbstractLifecycle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.stereotype.Service;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServer extends AbstractLifecycle {

    private final GatewayServerWebHandler handler;
    //    private final GatewayConfigProvider configService;
    private volatile WebServer webServer;

    @Override
    protected void doStart() {
//        GatewayConfig config = configService.getConfig();
        GatewayConfig config = GatewayConfig.builder()
            .server(
                GatewayConfig.Server.builder()
                    .port(80)
                    .name("ga")
                    .running(true)
                    .build()
            )
            .httpClient(
                GatewayConfig.HttpClient.builder()
                    .maxConnections(24)
                    .build()
            )
            .build();

        handler.start(config);
        HttpWebHandlerAdapter webHandlerAdapter = new HttpWebHandlerAdapter(handler);
        NettyReactiveWebServerFactory nettyReactiveWebServerFactory = new NettyReactiveWebServerFactory(config.getServer().getPort());
//			Ssl ssl=new Ssl();
//			ssl.setKeyStore("classpath:scg-keystore.p12");
//			ssl.setKeyStorePassword("111111");
//			ssl.setKeyStoreType("PKCS12");
//			ssl.setKeyAlias("gateway");
//        nettyReactiveWebServerFactory.setSsl(ssl);
        String serverName = config.getServer().getName();
        nettyReactiveWebServerFactory.setServerHeader(serverName);
        try {
            webServer = nettyReactiveWebServerFactory.getWebServer(webHandlerAdapter);
            webServer.start();
            Bootstrap.log(String.format("应用网关服务启动，监听端口:%s", webServer.getPort()));
        } catch (Exception e) {
            Bootstrap.error(String.format("应用网关服务启动失败，监听端口:%s", webServer.getPort()));
        }

    }

    @Override
    protected void doStop() {
        if (null != webServer) {
            webServer.stop();
        }
//        this.handler.stop();
    }
}

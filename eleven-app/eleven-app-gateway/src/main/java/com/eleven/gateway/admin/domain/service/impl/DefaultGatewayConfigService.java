package com.eleven.gateway.admin.domain.service.impl;

import com.cnetong.basic.domain.ParameterAccessor;
import com.eleven.gateway.admin.domain.service.GatewayConfigService;
import com.eleven.gateway.core.GatewayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;
import reactor.netty.resources.ConnectionProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGatewayConfigService implements GatewayConfigService {
    private final ParameterAccessor parameterAccessor;
    private final ConfigurableEnvironment environment;


    @Override
    public void saveConfig(GatewayConfig config) {
        parameterAccessor.updateParameter("gateway.httpClient.maxConnection", String.valueOf(config.getHttpClient().getMaxConnections()));
        parameterAccessor.updateParameter("gateway.server.name", String.valueOf(config.getServer().getName()));
        parameterAccessor.updateParameter("gateway.server.port", String.valueOf(config.getServer().getPort()));
        parameterAccessor.updateParameter("gateway.server.running", config.getServer().getRunning().toString());
    }

    @Override
    public GatewayConfig getConfig() {
        return GatewayConfig.builder()
                .httpClient(
                        GatewayConfig.HttpClient.builder()
                                .maxConnections(Integer.parseInt(parameterAccessor.getParameter("gateway.httpClient.maxConnection", String.valueOf(ConnectionProvider.DEFAULT_POOL_MAX_CONNECTIONS)).getValue()))
                                .build()
                )
                .server(
                        GatewayConfig.Server.builder()
                                .running(Boolean.getBoolean(parameterAccessor.getParameter("gateway.server.running", "false").getValue()))
                                .name(parameterAccessor.getParameter("gateway.server.name", environment.getProperty("spring.application.name")).getValue())
                                .port(Integer.parseInt(parameterAccessor.getParameter("gateway.server.port", "1024").getValue()))
                                .build()

                )

                .build();

    }
}

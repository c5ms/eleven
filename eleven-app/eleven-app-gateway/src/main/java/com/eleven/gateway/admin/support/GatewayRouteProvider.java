package com.eleven.gateway.admin.support;

import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.core.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayRouteProvider implements GatewayRouteBroker, GatewayProvider<GatewayRoute> {

    private final MetadataManager metadataManager;
    private final List<GatewayRouteLoader> loaders;
    private final List<GatewayRoute> proxies = new CopyOnWriteArrayList<>();

    protected synchronized void initialize() {
        proxies.clear();

        for (GatewayRouteLoader loader : loaders) {
            this.proxies.addAll(loader.load());
        }

        GatewayLoggers.RUNTIME_LOGGER.info("路由装载完毕,v {}", metadataManager.getGlobalVersion(GateAdminConstants.VERSION_KEY_ROUTES));
    }

    @Override
    public Collection<GatewayRoute> getRoutes() {
        metadataManager.syncVersion(GateAdminConstants.VERSION_KEY_ROUTES, version -> this.initialize());
        return proxies;
    }

    @Override
    public Collection<GatewayRoute> getInstances() {
        return proxies;
    }
}

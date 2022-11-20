package com.eleven.gateway.admin.support;

import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.core.GatewayLoggers;
import com.eleven.gateway.core.GatewayProvider;
import com.eleven.gateway.core.GatewayService;
import com.eleven.gateway.core.GatewayServiceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServiceProvider implements GatewayProvider<GatewayService> {

    private final MetadataManager metadataManager;
    private final List<GatewayServiceLoader> loaders;
    private final List<GatewayService> instances = new CopyOnWriteArrayList<>();

    public Collection<GatewayService> getInstances() {
        metadataManager.syncVersion(GateAdminConstants.VERSION_KEY_SERVICES, version -> this.initialize());
        return instances;
    }

    protected synchronized void initialize() {
        instances.clear();
        for (GatewayServiceLoader loader : loaders) {
            instances.addAll(loader.load());
        }
        GatewayLoggers.RUNTIME_LOGGER.info("服务装载完毕,v {}", metadataManager.getGlobalVersion(GateAdminConstants.VERSION_KEY_SERVICES));
    }

}

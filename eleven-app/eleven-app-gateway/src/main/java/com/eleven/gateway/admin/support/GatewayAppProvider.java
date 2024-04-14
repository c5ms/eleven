package com.eleven.gateway.admin.support;

import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.core.GatewayApp;
import com.eleven.gateway.core.GatewayAppLoader;
import com.eleven.gateway.core.GatewayLoggers;
import com.eleven.gateway.core.GatewayProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yz
 */
@Service
@RequiredArgsConstructor
public class GatewayAppProvider implements GatewayProvider<GatewayApp> {

    private final MetadataManager metadataManager;

    private final List<GatewayAppLoader> loaders;

    private final List<GatewayApp> instances = new CopyOnWriteArrayList<>();

    @Override
    public Collection<GatewayApp> getInstances() {
        metadataManager.syncVersion(GateAdminConstants.VERSION_KEY_APP, version -> this.initialize());
        return instances;
    }

    protected synchronized void initialize() {
        instances.clear();
        for (GatewayAppLoader loader : loaders) {
            instances.addAll(loader.loadApps());
        }
        GatewayLoggers.RUNTIME_LOGGER.info("服务装载完毕,v {}", metadataManager.getGlobalVersion(GateAdminConstants.VERSION_KEY_APP));
    }

}

package com.eleven.gateway.admin.support;

import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.core.GatewayLoggers;
import com.eleven.gateway.core.GatewayProvider;
import com.eleven.gateway.core.GatewayResource;
import com.eleven.gateway.core.GatewayResourceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayResourceProvider implements GatewayProvider<GatewayResource> {

    private final MetadataManager metadataManager;
    private final List<GatewayResourceLoader> loaders;
    private final List<GatewayResource> instances = new CopyOnWriteArrayList<>();

    public Collection<GatewayResource> getInstances() {
        metadataManager.syncVersion(GateAdminConstants.VERSION_KEY_RESOURCES, version -> this.initialize());
        return instances;
    }

    protected void initialize() {
        instances.clear();
        for (GatewayResourceLoader loader : loaders) {
            instances.addAll(loader.load());
        }
        GatewayLoggers.RUNTIME_LOGGER.info("服务装载完毕,v {}", metadataManager.getGlobalVersion(GateAdminConstants.VERSION_KEY_RESOURCES));

    }


}

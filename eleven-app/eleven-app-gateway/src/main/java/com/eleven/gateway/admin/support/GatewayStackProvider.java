package com.eleven.gateway.admin.support;

import com.cnetong.common.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.core.GatewayLoggers;
import com.eleven.gateway.core.GatewayProvider;
import com.eleven.gateway.core.GatewayStack;
import com.eleven.gateway.core.GatewayStackLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayStackProvider implements GatewayProvider<GatewayStack> {

    private final MetadataManager metadataManager;
    private final List<GatewayStackLoader> loaders;
    private final List<GatewayStack> instances = new CopyOnWriteArrayList<>();

    public Collection<GatewayStack> getInstances() {
        metadataManager.syncVersion(GateAdminConstants.VERSION_KEY_STACKS, version -> this.initialize());
        return instances;
    }

    protected synchronized void initialize() {
        instances.clear();
        for (GatewayStackLoader serverLoader : loaders) {
            instances.addAll(serverLoader.loadStacks());
        }
        GatewayLoggers.RUNTIME_LOGGER.info("站点装载完毕,v {}", metadataManager.getGlobalVersion(GateAdminConstants.VERSION_KEY_STACKS));

    }
}


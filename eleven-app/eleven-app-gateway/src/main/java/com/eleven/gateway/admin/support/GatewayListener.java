package com.eleven.gateway.admin.support;

import com.eleven.core.cluster.MetadataManager;
import com.eleven.gateway.admin.constants.GateAdminConstants;
import com.eleven.gateway.admin.domain.event.*;
import com.eleven.gateway.core.GatewayServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayListener {
    private final GatewayServer gatewayServer;
    private final MetadataManager metadataManager;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartedEvent() {
        gatewayServer.start();
    }

    @EventListener(GatewayRouteUpdatedEvent.class)
    public void onGatewayRoutesUpdatedEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_ROUTES);
    }

    @EventListener(GatewayStacksUpdatedEvent.class)
    public void onGatewayStacksUpdatedEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_STACKS);
    }

    @EventListener(GatewayServiceUpdatedEvent.class)
    public void onGatewayServiceUpdatedEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_SERVICES);
    }

    @EventListener(GateResourceUpdateEvent.class)
    public void on(GateResourceUpdateEvent event) {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_RESOURCES);
    }

    @EventListener(GatewayAppUpdatedEvent.class)
    public void onGatewayAppUpdatedEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_APP);
    }

    @EventListener(GatewayTokenUpdatedEvent.class)
    public void onGatewayTokenUpdatedEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_TOKEN);
    }


    @EventListener(GatewayRefreshEvent.class)
    public void onGatewayRefreshEvent() {
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_RESOURCES);
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_ROUTES);
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_STACKS);
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_SERVICES);

        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_APP);
        metadataManager.updateGlobalVersion(GateAdminConstants.VERSION_KEY_TOKEN);
    }

}

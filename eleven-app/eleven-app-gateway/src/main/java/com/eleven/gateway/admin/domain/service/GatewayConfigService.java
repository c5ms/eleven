package com.eleven.gateway.admin.domain.service;

import com.eleven.gateway.core.GatewayConfig;
import com.eleven.gateway.core.GatewayConfigProvider;

public interface GatewayConfigService extends GatewayConfigProvider {
    void saveConfig(GatewayConfig config);
}

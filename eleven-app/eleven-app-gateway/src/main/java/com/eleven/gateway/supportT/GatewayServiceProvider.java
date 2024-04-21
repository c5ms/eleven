package com.eleven.gateway.supportT;

import com.eleven.gateway.core.GatewayProvider;
import com.eleven.gateway.core.GatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServiceProvider implements GatewayProvider<GatewayService> {

    @Override
    public Collection<GatewayService> getInstances() {
        return null;
    }
}

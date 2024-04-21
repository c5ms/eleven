package com.eleven.gateway.supportT;

import com.eleven.gateway.core.GatewayProvider;
import com.eleven.gateway.core.GatewayStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayStackProvider implements GatewayProvider<GatewayStack> {

    @Override
    public Collection<GatewayStack> getInstances() {
        return null;
    }
}


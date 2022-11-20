package com.eleven.gateway.supportT;

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

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartedEvent() {
        gatewayServer.start();
    }


}

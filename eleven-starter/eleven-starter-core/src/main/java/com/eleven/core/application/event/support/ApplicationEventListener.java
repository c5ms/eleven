package com.eleven.core.application.event.support;

import com.eleven.core.application.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final ApplicationEventIntegrator integrator;

    @EventListener
    public void onEvent(ApplicationEvent event) throws ApplicationEventSerializeException {
        if (null == event.getClass().getAnnotation(IntegrationEvent.class)) {
            return;
        }
        if (event.getHeader().getFrom() != ApplicationEventOrigin.INTERNAL) {
            return;
        }
        integrator.send(event);
    }

}

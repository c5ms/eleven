package com.eleven.framework.event.support;

import com.eleven.framework.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final ApplicationEventIntegrator integrator;

    @Order
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
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

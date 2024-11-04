package com.eleven.booking.application.listener;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.domain.DomainEvent;
import com.eleven.booking.application.support.BookingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingListener {

    @EventListener
    public void on(DomainEvent event) {
        log.debug("handle domain event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
        toApplicationEvent(event).ifPresent(BookingContext::publishEvent);
    }

    @EventListener
    public void on(ApplicationEvent event) {
        log.debug("handle application event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
    }

    public Optional<ApplicationEvent> toApplicationEvent(DomainEvent domainEvent) {
        ApplicationEvent applicationEvent = null;

        // todo convert domain-event to application-event

        return Optional.ofNullable(applicationEvent);
    }
}

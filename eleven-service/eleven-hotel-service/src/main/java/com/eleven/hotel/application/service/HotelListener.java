package com.eleven.hotel.application.service;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.domain.DomainEvent;
import com.eleven.hotel.api.application.event.PlanStartedSaleEvent;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.plan.event.PlanStarted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelListener {

    @EventListener
    public void on(DomainEvent event) {
        log.debug("handle domain event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
        toApplicationEvent(event).ifPresent(HotelContext::publishEvent);
    }

    @EventListener
    public void on(ApplicationEvent event) {
        log.debug("handle application event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
    }


    public Optional<ApplicationEvent> toApplicationEvent(DomainEvent domainEvent) {
        ApplicationEvent applicationEvent = null;

        if (domainEvent instanceof PlanStarted e) {
            applicationEvent = new PlanStartedSaleEvent().setPlanId(e.getPlan().getPlanId());
        }

        return Optional.ofNullable(applicationEvent);
    }
}

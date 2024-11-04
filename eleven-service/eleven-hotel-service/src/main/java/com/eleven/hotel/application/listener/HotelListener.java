package com.eleven.hotel.application.listener;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.domain.DomainEvent;
import com.eleven.hotel.application.support.HotelContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelListener {
    private final ConversionService conversionService;

    @EventListener
    public void on(DomainEvent event) {
        log.debug("handle domain event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));

        ApplicationEvent applicationEvent = null;
        if (conversionService.canConvert(event.getClass(), ApplicationEvent.class)) {
            applicationEvent = conversionService.convert(event, ApplicationEvent.class);
        }

        HotelContext.publishEvent(applicationEvent);
    }

    @EventListener
    public void on(ApplicationEvent event) {
        log.debug("handle application event {}: {}", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
    }


}

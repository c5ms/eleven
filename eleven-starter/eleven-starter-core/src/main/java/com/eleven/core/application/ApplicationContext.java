package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.event.ApplicationEvent;
import com.eleven.core.event.DomainEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationContext {

    public void publishEvent(DomainEvent domainEvent) {
        SpringUtil.publishEvent(domainEvent);
    }

    public void publishEvent(ApplicationEvent applicationEvent) {
        SpringUtil.publishEvent(applicationEvent);
    }

}

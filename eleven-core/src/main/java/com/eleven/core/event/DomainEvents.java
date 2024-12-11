package com.eleven.core.event;

import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainEvents {

    public void publish(DomainEvent event) {
        SpringUtil.publishEvent(event);
    }
}

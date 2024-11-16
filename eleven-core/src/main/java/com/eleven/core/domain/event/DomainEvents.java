package com.eleven.core.domain.event;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.domain.error.DomainEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainEvents {

    public void publish(DomainEvent event) {
        SpringUtil.publishEvent(event);
    }
}

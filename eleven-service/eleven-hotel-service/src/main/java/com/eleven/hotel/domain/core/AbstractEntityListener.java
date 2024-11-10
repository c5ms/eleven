package com.eleven.hotel.domain.core;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.domain.DomainEvent;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractEntityListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(AbstractEntity entity) {

    }

    @PostLoad
    private void beforeAnyLoad(AbstractEntity entity) {

    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(AbstractEntity entity) {
        for (DomainEvent domainEvent : entity.domainEvents()) {
            SpringUtil.publishEvent(domainEvent);
        }
        entity.clearDomainEvents();
    }


}

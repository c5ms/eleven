package com.eleven.hotel.domain.core;

import com.eleven.core.domain.DomainEvent;
import com.eleven.core.domain.DomainContext;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractEntityListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(AbstractEntity entity) {

    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(AbstractEntity entity) {
        for (DomainEvent domainEvent : entity.domainEvents()) {
            DomainContext.publishEvent(domainEvent);
        }
        entity.clearDomainEvents();
    }


}

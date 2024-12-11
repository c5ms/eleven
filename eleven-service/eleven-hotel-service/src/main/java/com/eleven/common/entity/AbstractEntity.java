package com.eleven.common.entity;

import com.eleven.core.domain.error.DomainEvent;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class AbstractEntity {

    private transient final @Transient Map<Class<?>, DomainEvent> domainEvents = new HashMap<>();

    @DomainEvents
    protected Collection<DomainEvent> events() {
        return domainEvents.values();
    }

    @AfterDomainEventPublication
    protected void clearEvents() {
        domainEvents.clear();
    }

    protected void addEvent(DomainEvent event) {
        this.domainEvents.put(event.getClass(), event);
    }

    // commonly for test
    public boolean hasEvents(Class<?> eventClass) {
        return domainEvents.containsKey(eventClass);
    }

}

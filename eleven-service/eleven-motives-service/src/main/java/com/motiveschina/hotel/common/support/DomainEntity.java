package com.motiveschina.hotel.common.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.eleven.framework.event.DomainEvent;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

@MappedSuperclass
public abstract class DomainEntity {

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

package com.eleven.hotel.domain.core;

import com.eleven.core.domain.error.DomainEvent;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@MappedSuperclass
@TableGenerator(table = AbstractEntity.GENERATOR_TABLE, name = AbstractEntity.GENERATOR_NAME, allocationSize = 5)
public abstract class AbstractEntity {

    protected static final String GENERATOR_NAME = "hms_generator";
    protected static final String GENERATOR_TABLE = "hms_sequences";

    private transient final @Transient Map<Class<?>, DomainEvent> domainEvents = new HashMap<>();

    @DomainEvents
    protected Collection<DomainEvent> domainEvents() {
        return domainEvents.values();
    }

    @AfterDomainEventPublication
    protected void clearEvents() {
        domainEvents.clear();
    }

    protected void addEvent(DomainEvent event) {
        this.domainEvents.put(event.getClass(), event);
    }

}

package com.eleven.booking.domain.core;

import com.eleven.core.domain.DomainEvent;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@EntityListeners(AbstractEntityListener.class)
@FieldNameConstants
@MappedSuperclass
@TableGenerator(table = AbstractEntity.GENERATOR_TABLE, name = AbstractEntity.GENERATOR_NAME, allocationSize = 5)
public abstract class AbstractEntity {

    public static final String GENERATOR_NAME = "bk_generator";
    public static final String GENERATOR_TABLE = "bk_sequences";

    @Getter(AccessLevel.PROTECTED)
    private transient final @Transient List<DomainEvent> domainEvents = new ArrayList<>();

    protected <T extends DomainEvent> void addEvent(T event) {
        Assert.notNull(event, "Domain event must not be null");
        this.domainEvents.add(event);
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    protected Collection<DomainEvent> domainEvents() {
        if (null == domainEvents) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(domainEvents);
    }

}

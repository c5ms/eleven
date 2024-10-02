package com.eleven.core.data;

import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@FieldNameConstants
public abstract class AbstractEntity implements Persistable<String> {
    private transient final @Transient List<Object> domainEvents = new ArrayList<>();

    @Transient
    private boolean isNew = true;

    public void markNew() {
        this.isNew = true;
    }

    public void markOld() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    protected <T> T addEvent(T event) {
        Assert.notNull(event, "Domain event must not be null");
        this.domainEvents.add(event);
        return event;
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }


    @DomainEvents
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}

package com.eleven.hotel.domain.core;

import com.eleven.core.domain.DomainEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import java.util.*;

@Getter
@EntityListeners(AbstractEntityListener.class)
@FieldNameConstants
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hms_generator")
    @TableGenerator(name = "hms_generator", table = "hms_sequences")
    private Integer id;

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
        return Collections.unmodifiableList(domainEvents);
    }

    public <T extends AbstractEntity> boolean is(T target) {
        if (target.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }
        return Objects.equals(target.getId(), this.getId());
    }

}

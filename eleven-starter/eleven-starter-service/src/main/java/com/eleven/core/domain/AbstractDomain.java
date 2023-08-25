package com.eleven.core.domain;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

/**
 * 新旧原则： 默认情况下，一个对象是旧的，你如果你认为这是一个新的，请在对象上标记他是新对象。
 *
 * @param <T>
 */
@Getter
@FieldNameConstants
public abstract class AbstractDomain<T extends AbstractDomain<T>>
        extends AbstractAggregateRoot<T>
        implements Persistable<String> {


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

}

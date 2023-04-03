package com.demcia.eleven.security.domain;

import lombok.Getter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

/**
 * 新旧原则： 默认情况下，一个对象是旧的，你如果你认为这是一个新的，请在对象上标记他是新对象。
 *
 * @param <T>
 */
@Getter
public abstract class AbstractDomain<T extends AbstractDomain<T>>
        extends AbstractAggregateRoot<T>
        implements Persistable<String> {

    @Transient
    private Boolean isNew = null;

    protected void markNew() {
        this.isNew = true;
    }

    protected void markOld() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        if (null == isNew) {
            return null == getId();
        }
        return isNew;
    }

}

package com.eleven.core.data;

import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@FieldNameConstants
public abstract class AbstractEntity implements Persistable<String> {

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

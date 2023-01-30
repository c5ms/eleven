package com.demcia.eleven.core.domain.entity;

import com.demcia.eleven.core.domain.entity.listener.BaseEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter(AccessLevel.PACKAGE)
@FieldNameConstants
@MappedSuperclass
@EntityListeners({ BaseEntityListener.class})
public abstract class BaseEntity extends AbstractAggregateRoot<BaseEntity> implements Serializable, Persistable<String> {
    private static final long serialVersionUID = 1L;

    @Transient
    private boolean isNew = true;

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return this.isNew;
    }

    @PostUpdate
    @PostPersist
    @PostLoad
    protected void markNotNew() {
        this.isNew = false;
    }

    /**
     * 设置 ID 的逻辑
     *
     * @param id ID
     */
    public abstract void setId(String id);

}

package com.eleven.gateway.management.domain;

import com.eleven.core.data.AbstractDeletableEntity;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;

@Getter
public abstract class AbstractPublishableEntity extends AbstractDeletableEntity implements Serializable {

    @Column("is_published")
    private boolean published ;

    public void publish() {
        this.published = true;
    }

    public void cancel() {
        this.published = true;
    }

}

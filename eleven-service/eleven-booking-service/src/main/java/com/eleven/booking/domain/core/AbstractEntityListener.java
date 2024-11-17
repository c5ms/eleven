package com.eleven.booking.domain.core;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractEntityListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(AbstractEntity entity) {

    }

    @PostLoad
    private void beforeAnyLoad(AbstractEntity entity) {

    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(AbstractEntity entity) {

    }


}

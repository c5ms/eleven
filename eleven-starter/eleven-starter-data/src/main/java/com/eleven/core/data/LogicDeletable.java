package com.eleven.core.data;

import java.time.LocalDateTime;

public interface LogicDeletable {
    LocalDateTime getDeleteAt();

    default boolean isDeleted() {
        return getDeleteAt() != null;
    }

    default boolean isEffective() {
        return !isDeleted();
    }
}

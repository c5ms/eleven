package com.demcia.eleven.domain.identity;

import org.apache.commons.lang3.StringUtils;

public interface IdGenerator {

    String nextId(String schema);

    default String nextId(Class<?> schema) {
        return nextId(schema.getSimpleName());
    }

    default String nextId() {
        return nextId(StringUtils.EMPTY);
    }
}

package com.eleven.access.core;

import java.util.Collection;

public interface ResourceContext {
    <T> Resource<T> getResource(String id);

    Collection<? extends Resource<?>> listResources();

    void removeResource(String id);
}

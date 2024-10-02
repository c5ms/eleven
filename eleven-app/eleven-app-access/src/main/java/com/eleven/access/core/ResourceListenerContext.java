package com.eleven.access.core;

import java.util.Collection;

public interface ResourceListenerContext {
    Collection<? extends ResourceListener> listListeners();

    void removeListener(String id);
}

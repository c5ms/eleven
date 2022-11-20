package com.eleven.access.core;

import java.util.Collection;

public interface ResourceProducerContext {
    ResourceProducer getProducer(String id);

    void removeProducer(String id);

    Collection<? extends ResourceProducer> listProducers();
}

package com.eleven.access.core;

import java.util.Map;

public interface Resource<Actual> extends Checkable {

    Actual getActual();

    void update(Map<String, String> config);

    void destroy();

    ResourceProducer createProducer(Map<String, String> config);

    ResourceConsumer createConsumer(Map<String, String> config);
}

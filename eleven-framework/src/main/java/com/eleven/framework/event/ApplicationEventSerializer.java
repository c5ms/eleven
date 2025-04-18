package com.eleven.framework.event;

import java.util.Optional;

public interface ApplicationEventSerializer {
    ApplicationEventMessage serialize(ApplicationEvent event) throws ApplicationEventSerializeException;

    Optional<ApplicationEvent> deserialize(ApplicationEventMessage message) throws ApplicationEventSerializeException;
}

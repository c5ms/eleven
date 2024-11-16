package com.eleven.core.application.event;

import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent("SystemTestEvent")
public class TestEvent extends AbstractApplicationEvent {
    private String content;
}

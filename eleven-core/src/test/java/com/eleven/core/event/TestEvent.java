package com.eleven.core.event;

import com.eleven.core.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent("SystemTestEvent")
public class TestEvent extends AbstractApplicationEvent {
    private String content;
}

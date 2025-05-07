package com.eleven.framework.event;

import com.eleven.framework.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent("SystemTestEvent")
public class TestEvent extends AbstractApplicationEvent {
    private String content;
}

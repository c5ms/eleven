package com.eleven.core.application.event;

import lombok.Getter;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@TestComponent
public class TestEventListener {

    private final List<ApplicationEvent> events = new ArrayList<>();

    @EventListener
    public void send(ApplicationEvent event) {
        events.add(event);
    }
}

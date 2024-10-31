package com.eleven.core.application.event;

import lombok.Getter;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;

@Getter
@TestComponent
public class TestEventSender implements ApplicationEventMessageSender {
    private final List<ApplicationEventMessage> messages = new ArrayList<>();

    @Override
    public void send(ApplicationEventMessage message) {
        System.out.println(message);
        messages.add(message);
    }
}

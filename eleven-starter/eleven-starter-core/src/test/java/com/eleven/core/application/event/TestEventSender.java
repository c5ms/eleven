package com.eleven.core.application.event;

import lombok.Getter;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@TestComponent
public class TestEventSender implements ApplicationEventMessageSender {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void send(String message) {
        System.out.println(message);
        messages.add(message);
    }
}

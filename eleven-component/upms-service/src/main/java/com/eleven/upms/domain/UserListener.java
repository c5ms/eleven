package com.eleven.upms.domain;

import com.eleven.core.event.ElevenEvent;
import com.eleven.core.message.MessageTemplate;
import com.eleven.upms.model.UserCreatedEvent;
import com.eleven.upms.model.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {
    private final MessageTemplate messageTemplate;

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent event) {
        messageTemplate.send("events", "user_created", event);
    }

    @EventListener(UserUpdatedEvent.class)
    public void onCreate(UserUpdatedEvent event) {
        System.out.println("用户更新" + event.userId());
    }

    @EventListener(ElevenEvent.class)
    public void on(ElevenEvent event) {
        System.out.println("event happened ---> " + event);
    }

}

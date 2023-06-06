package com.eleven.upms.application;

import com.eleven.core.message.MessageManager;
import com.eleven.upms.domain.event.UserCreatedEvent;
import com.eleven.upms.domain.event.UserGrantedEvent;
import com.eleven.upms.domain.event.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {
    private final MessageManager messageManager;
    private final UserConverter userConverter;

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent event) {
        var msg = userConverter.toDto(event);
        messageManager.send("events", "user_created", msg);
    }

    @EventListener(UserUpdatedEvent.class)
    public void onCreate(UserUpdatedEvent event) {
        System.out.println("用户更新" + event.userId());
    }

    @EventListener(UserGrantedEvent.class)
    public void onUserGrantedEvent(UserGrantedEvent event) {
        System.out.println("用户被授权" + event.userId());
    }

}

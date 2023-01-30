package com.demcia.eleven.upms.domain.listener;

import com.demcia.eleven.upms.domain.event.UserCreatedEvent;
import com.demcia.eleven.upms.domain.event.UserUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent userCreatedEvent) {
        System.out.println("用户创建");
    }

    @EventListener(UserUpdatedEvent.class)
    public void onCreate(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("用户更新");
    }
}

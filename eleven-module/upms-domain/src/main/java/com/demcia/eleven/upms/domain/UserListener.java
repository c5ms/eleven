package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.event.UserCreatedEvent;
import com.demcia.eleven.upms.domain.event.UserGrantedEvent;
import com.demcia.eleven.upms.domain.event.UserUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent event) {
        System.out.println("用户创建" + event.userId());
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

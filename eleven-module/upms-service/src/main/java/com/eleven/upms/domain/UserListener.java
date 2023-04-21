package com.eleven.upms.domain;

import com.eleven.upms.domain.event.UserCreatedEvent;
import com.eleven.upms.domain.event.UserGrantedEvent;
import com.eleven.upms.domain.event.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
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

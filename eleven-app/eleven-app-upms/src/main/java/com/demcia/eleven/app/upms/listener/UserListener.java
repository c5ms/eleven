package com.demcia.eleven.app.upms.listener;

import com.demcia.eleven.domain.upms.events.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @EventListener(UserCreatedEvent.class)
    public  void onCreate(UserCreatedEvent userCreatedEvent){
        System.out.println("用户创建");
    }
}

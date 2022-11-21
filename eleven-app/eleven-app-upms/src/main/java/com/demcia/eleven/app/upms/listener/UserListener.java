package com.demcia.eleven.app.upms.listener;

import com.demcia.eleven.domain.upms.events.UserCreatedEvent;
import com.demcia.eleven.domain.upms.events.UserUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @EventListener(UserCreatedEvent.class)
    public  void onCreate(UserCreatedEvent userCreatedEvent){
        System.out.println("用户创建");
    }

    @EventListener(UserUpdateEvent.class)
    public  void onCreate(UserUpdateEvent userUpdateEvent){
        System.out.println("用户更新");
    }
}

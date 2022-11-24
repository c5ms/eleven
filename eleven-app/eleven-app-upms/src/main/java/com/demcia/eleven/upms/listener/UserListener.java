package com.demcia.eleven.upms.listener;

import com.demcia.eleven.upms.core.events.UserCreatedEvent;
import com.demcia.eleven.upms.core.events.UserUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @EventListener(UserCreatedEvent.class)
    public  void onCreate(UserCreatedEvent userCreatedEvent){
        System.out.println("用户创建");
    }

    @EventListener(UserUpdatedEvent.class)
    public  void onCreate(UserUpdatedEvent userUpdatedEvent){
        System.out.println("用户更新");
    }
}

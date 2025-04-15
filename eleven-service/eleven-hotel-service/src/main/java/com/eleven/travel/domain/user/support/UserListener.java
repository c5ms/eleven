package com.eleven.travel.domain.user.support;

import com.eleven.domain.user.model.UserRepository;
import com.eleven.upms.api.domain.event.UserCreatedEvent;
import com.eleven.upms.api.domain.event.UserStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {
    private final UserRepository userRepository;

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent event) {
        var userId = event.getUserId();
        userRepository.findById(userId).ifPresent(user -> log.debug("用户创建(本地):{}", user.getUsername()));
        log.debug("用户创建:{}", event.getUserId());
    }

    @EventListener(UserStatusChangedEvent.class)
    public void onCreate(UserStatusChangedEvent event) {
        var userId = event.getUserId();
        userRepository.findById(userId).ifPresent(user -> log.debug("用户更新(本地):{}", user.getUsername()));
        log.debug("用户更新:{}", event.getUserId());
    }


}

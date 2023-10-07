package com.eleven.upms.domain;

import com.eleven.core.event.ElevenEvent;
import com.eleven.upms.event.UserCreatedEvent;
import com.eleven.upms.event.UserUpdatedEvent;
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

    }

    @EventListener(UserUpdatedEvent.class)
    public void onCreate(UserUpdatedEvent event) {
        var userId = event.userId();
        userRepository.findById(userId).ifPresent(user -> log.debug("用户更新(本地):{}", user.getNickname()));
        log.debug("用户更新:{}", event.userId());
    }

    @EventListener(ElevenEvent.class)
    public void on(ElevenEvent event) {
        log.debug("事件发生:{}", event);
    }

}

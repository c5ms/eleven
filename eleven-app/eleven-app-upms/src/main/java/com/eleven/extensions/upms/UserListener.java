package com.eleven.extensions.upms;

import com.eleven.core.event.ElevenEvent;
import com.eleven.upms.domain.UserService;
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
    private final UserService userService;

    @EventListener(UserCreatedEvent.class)
    public void onCreate(UserCreatedEvent event) {

    }

    @EventListener(UserUpdatedEvent.class)
    public void onCreate(UserUpdatedEvent event) {
        var userId = event.userId();
        var user = userService.getUser(userId);
        user.ifPresent(user1 -> log.debug("用户更新(本地):{}", user1.getNickname()));
        log.debug("用户更新:{}", event.userId());
    }

    @EventListener(ElevenEvent.class)
    public void on(ElevenEvent event) {
        log.debug("事件发生:{}", event);
    }

}

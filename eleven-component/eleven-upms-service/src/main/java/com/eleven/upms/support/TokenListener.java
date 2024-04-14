package com.eleven.upms.support;

import com.eleven.core.security.SubjectStore;
import com.eleven.core.security.TokenStore;
import com.eleven.core.time.TimeContext;
import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenRepository;
import com.eleven.upms.domain.UserRepository;
import com.eleven.upms.model.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenListener {
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final TokenStore tokenStore;
    private final SubjectStore subjectStore;

    //    @Async
//    @EventListener(ApplicationStartedEvent.class)
    public void on(ApplicationStartedEvent e) {
        var now = TimeContext.localDateTime();
        var tokens = accessTokenRepository.findValidToken(now);
        tokens.stream()
            .parallel()
            .map(AccessToken::toToken)
            .peek(token -> log.info("restore token {}", token))
            .forEach(tokenStore::save);
    }

    @EventListener(UserDeletedEvent.class)
    public void on(UserDeletedEvent e) {
        var now = TimeContext.localDateTime();
        var user = userRepository.requireById(e.userId());
        var owner = new AccessToken.Owner(user.toPrincipal());
        subjectStore.remove(user.toPrincipal());
        var tokens = accessTokenRepository.findValidTokenByOwner(owner, now);
        for (AccessToken token : tokens) {
            tokenStore.remove(token.getToken());
        }
        accessTokenRepository.expireAllByOwner(owner, now);
    }
}

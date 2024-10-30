package com.eleven.upms.application.support;

import com.eleven.core.authorization.SubjectStore;
import com.eleven.core.authorization.TokenStore;
import com.eleven.core.time.TimeHelper;
import com.eleven.upms.api.domain.event.UserDeletedEvent;
import com.eleven.upms.domain.model.AccessToken;
import com.eleven.upms.domain.model.AccessTokenRepository;
import com.eleven.upms.domain.model.UserRepository;
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
        var now = TimeHelper.localDateTime();
        var tokens = accessTokenRepository.findValidToken(now);
        tokens.stream()
            .parallel()
            .map(AccessToken::toToken)
            .peek(token -> log.info("restore token {}", token))
            .forEach(tokenStore::save);
    }

    @EventListener(UserDeletedEvent.class)
    public void on(UserDeletedEvent e) {
        var now = TimeHelper.localDateTime();
        var user = userRepository.requireById(e.getUserId());
        var owner = new AccessToken.Owner(user.toPrincipal());
        subjectStore.remove(user.toPrincipal());
        var tokens = accessTokenRepository.findValidTokenByOwner(owner, now);
        for (AccessToken token : tokens) {
            tokenStore.remove(token.getToken());
        }
        accessTokenRepository.expireAllByOwner(owner, now);
    }
}

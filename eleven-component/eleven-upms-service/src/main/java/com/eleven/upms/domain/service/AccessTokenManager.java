package com.eleven.upms.domain.service;

import com.eleven.core.event.EventSupport;
import com.eleven.core.security.Token;
import com.eleven.upms.core.event.AccessTokenCreatedEvent;
import com.eleven.upms.domain.model.AccessToken;
import com.eleven.upms.domain.model.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessTokenManager {
    private final EventSupport eventSupport;
    private final AccessTokenRepository accessTokenRepository;

    public void saveToken(Token token) {
        var accessToken = new AccessToken(token);
        accessTokenRepository.save(accessToken);
        eventSupport.publishInternalEvent(new AccessTokenCreatedEvent(token.getValue()));
    }

    public Optional<AccessToken> readToken(String tokenValue) {
        return accessTokenRepository.findById(tokenValue);
    }


    public void deleteToken(String token) {
        accessTokenRepository.deleteById(token);
    }
}

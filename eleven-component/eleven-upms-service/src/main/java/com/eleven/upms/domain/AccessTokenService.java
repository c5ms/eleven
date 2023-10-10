package com.eleven.upms.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.security.Token;
import com.eleven.upms.model.AccessTokenCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {
    private final AccessTokenRepository accessTokenRepository;

    public void saveToken(Token token) {
        var accessToken = new AccessToken(token);
        accessTokenRepository.save(accessToken);
        SpringUtil.publishEvent(new AccessTokenCreatedEvent(token.getValue()));
    }

    public Optional<AccessToken> readToken(String tokenValue) {
        return accessTokenRepository.findById(tokenValue);
    }


    public void deleteToken(String token) {
        accessTokenRepository.deleteById(token);
    }
}

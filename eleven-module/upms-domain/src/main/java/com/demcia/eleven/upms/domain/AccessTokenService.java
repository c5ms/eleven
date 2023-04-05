package com.demcia.eleven.upms.domain;

import com.demcia.eleven.core.security.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {
    private final AccessTokenRepository accessTokenRepository;

    public AccessToken createToken(Token token) {
        var accessToken = new AccessToken(token);
        accessTokenRepository.save(accessToken);
        return accessToken;
    }

    public Optional<AccessToken> readToken(String tokenValue) {
        return accessTokenRepository.findById(tokenValue);
    }


}

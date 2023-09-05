package com.eleven.upms.domain;

import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {
    private final AccessTokenRepository accessTokenRepository;

    public void saveToken(AccessToken accessToken) {
        accessTokenRepository.save(accessToken);
    }

    public Optional<AccessToken> readToken(String tokenValue) {
        return accessTokenRepository.findById(tokenValue);
    }


}

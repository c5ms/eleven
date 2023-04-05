package com.demcia.eleven.upms.application.support;

import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.core.security.TokenReader;
import com.demcia.eleven.upms.application.strategy.ConditionalOnUseLocalAuthenticate;
import com.demcia.eleven.upms.domain.AccessToken;
import com.demcia.eleven.upms.domain.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
@ConditionalOnUseLocalAuthenticate
@RequiredArgsConstructor
public class LocalTokenReader implements TokenReader {
    private final AccessTokenService accessTokenService;

    @Override
    public Optional<Token> read(String value) {
        return accessTokenService.readToken(value)
                .map(AccessToken::toToken);
    }

}

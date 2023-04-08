package com.eleven.security.support;

import com.eleven.core.security.Token;
import com.eleven.core.security.TokenReader;
import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LocalTokenReader implements TokenReader {
    private final AccessTokenService accessTokenService;

    @Override
    public Optional<Token> read(String value) {
        return accessTokenService.readToken(value)
                .map(AccessToken::toToken);
    }

}

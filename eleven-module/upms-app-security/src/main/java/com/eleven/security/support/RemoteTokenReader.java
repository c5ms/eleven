package com.eleven.security.support;

import com.eleven.core.security.Token;
import com.eleven.core.security.TokenReader;
import com.eleven.upms.app.client.AccessTokenClient;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RemoteTokenReader implements TokenReader {
    private final AccessTokenClient accessTokenClient;

    @Override
    public Optional<Token> read(String token) {
        return accessTokenClient.readToken(token);
    }
}

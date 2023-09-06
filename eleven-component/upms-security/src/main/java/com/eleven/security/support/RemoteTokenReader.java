package com.eleven.security.support;

import com.eleven.core.security.Token;
import com.eleven.core.security.TokenReader;
import com.eleven.upms.client.UpmsClient;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RemoteTokenReader implements TokenReader {
    private final UpmsClient upmsClient;

    @Override
    public Optional<Token> read(String token) {
        return upmsClient.readToken(token);
    }
}

package com.demcia.eleven.upms.application.support;

import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.core.security.TokenReader;
import com.demcia.eleven.upms.application.client.UpmsClient;
import com.demcia.eleven.upms.application.strategy.ConditionalOnUseRemoteAuthenticate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnUseRemoteAuthenticate
@RequiredArgsConstructor
public class RemoteTokenReader implements TokenReader {
    private final UpmsClient upmsClient;

    @Override
    public Optional<Token> read(String token) {
        return upmsClient.readToken(token);
    }
}

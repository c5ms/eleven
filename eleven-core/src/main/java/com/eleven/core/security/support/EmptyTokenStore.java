package com.eleven.core.security.support;

import com.eleven.core.security.Token;
import com.eleven.core.security.TokenStore;

import java.util.Optional;

public class EmptyTokenStore implements TokenStore {
    @Override
    public void save(Token token) {

    }

    @Override
    public void remove(String tokenValue) {

    }

    @Override
    public Optional<Token> retrieval(String tokenValue) {
        return Optional.empty();
    }
}

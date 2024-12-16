package com.eleven.framework.security.support;

import com.eleven.framework.security.Token;
import com.eleven.framework.security.TokenStore;

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

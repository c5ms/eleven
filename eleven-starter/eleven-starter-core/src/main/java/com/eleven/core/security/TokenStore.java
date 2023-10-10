package com.eleven.core.security;

import java.util.Optional;


public interface TokenStore {

    void save(Token token);

    void remove(String tokenValue);

    Optional<Token> retrieval(String tokenValue);

}

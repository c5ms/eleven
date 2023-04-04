package com.demcia.eleven.core.security;

import java.util.Optional;


public interface TokenStore {

    void save(Token token);

    Optional<Token> retrieval(String tokenValue);

}

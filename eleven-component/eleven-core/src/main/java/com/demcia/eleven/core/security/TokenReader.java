package com.demcia.eleven.core.security;

import java.util.Optional;


public interface TokenReader {

    Optional<Token> read(String token);

}

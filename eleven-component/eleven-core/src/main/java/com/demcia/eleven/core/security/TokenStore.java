package com.demcia.eleven.core.security;

import com.demcia.eleven.core.time.TimeContext;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;


public interface TokenStore  {

    void save(Token token);

    Optional<Token> retrieval(String tokenValue);

}

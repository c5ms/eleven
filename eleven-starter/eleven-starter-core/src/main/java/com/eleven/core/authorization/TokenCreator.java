package com.eleven.core.authorization;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

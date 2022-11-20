package com.eleven.core.security;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

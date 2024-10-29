package com.eleven.core.auth;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

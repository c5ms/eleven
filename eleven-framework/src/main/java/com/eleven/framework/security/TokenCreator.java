package com.eleven.framework.security;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

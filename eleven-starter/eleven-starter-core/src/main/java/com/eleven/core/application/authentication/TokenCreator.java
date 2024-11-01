package com.eleven.core.application.authentication;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

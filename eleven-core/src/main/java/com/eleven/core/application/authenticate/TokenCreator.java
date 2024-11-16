package com.eleven.core.application.authenticate;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

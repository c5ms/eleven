package com.eleven.core.authenticate;

public interface TokenCreator {

    Token create(Principal principal, TokenDetail detail);

}

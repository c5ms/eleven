package com.eleven.upms.endpoint.service;

import com.eleven.core.rest.annonation.AsServiceApi;
import com.eleven.core.security.*;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.domain.UserConvertor;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AsServiceApi
@RequiredArgsConstructor
public class UpmsServiceApiV1 implements UpmsClient {

    private final UserService userService;
    private final UserConvertor userConvertor;
    private final SecurityService securityService;

    // unused
    public Optional<Token> readToken(String token) {
        log.debug("readToken api invoked for {}", token);
        return securityService.verifyToken(token);
    }

    @Override
    public Subject createSubject(String type, String name) {
        log.debug("readSubject api invoked for {}#{}", type, name);
        var principal = new Principal(type, name);
        return securityService.createSubject(principal);
    }

    @Override
    public Optional<UserDto> readUser(String id) {
        log.debug("readUser api invoked for {}", id);
        return userService.getUser(id).map(userConvertor::toDto);
    }


}

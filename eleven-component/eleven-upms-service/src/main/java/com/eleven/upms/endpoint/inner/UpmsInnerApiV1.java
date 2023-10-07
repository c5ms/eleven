package com.eleven.upms.endpoint.inner;

import com.eleven.core.web.annonation.AsInnerApi;
import com.eleven.core.security.*;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AsInnerApi
@Tag(name = "用户权限管理接口")
@RequiredArgsConstructor
public class UpmsInnerApiV1 implements UpmsClient {

    private final UserService userService;
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
        return userService.getUser(id);
    }


}

package com.eleven.upms.endpoint.inner;

import com.eleven.core.security.Principal;
import com.eleven.core.security.SecurityService;
import com.eleven.core.security.Subject;
import com.eleven.core.web.annonation.AsInnerApi;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.UserDto;
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
    private final AccessTokenService accessTokenService;

    // unused
//    public Optional<Token> readToken(String token) {
//        log.debug("readToken api invoked for {}", token);
//        return securityService.verifyToken(token);
//    }

    public void deleteToken(String token) {
        log.debug("deleteToken api invoked for {}", token);
        securityService.invalidToken(token);
        accessTokenService.deleteToken(token);
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

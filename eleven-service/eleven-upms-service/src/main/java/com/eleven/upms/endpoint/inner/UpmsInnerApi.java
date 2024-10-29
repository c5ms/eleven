package com.eleven.upms.endpoint.inner;

import com.eleven.core.auth.Subject;
import com.eleven.core.auth.Token;
import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.upms.application.service.TokenService;
import com.eleven.upms.application.service.UserService;
import com.eleven.upms.api.endpoint.UpmsClient;
import com.eleven.upms.api.application.model.UserDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AsInternalApi
@Tag(name = "upms")
@RequiredArgsConstructor
public class UpmsInnerApi implements UpmsClient {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public Subject createSubject(String type, String name) {
        return tokenService.createSubject(type, name);
    }

    @Override
    public Optional<Token> readToken(String token) {
        return tokenService.readToken(token);
    }

    @Override
    public Optional<UserDetail> readUser(String id) {
        return userService.getUser(id);
    }

}

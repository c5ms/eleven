package com.eleven.upms.endpoint.inner;

import com.eleven.core.rest.annonation.AsInnerApi;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectManager;
import com.eleven.core.security.Token;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserConvertor;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@AsInnerApi
@RequiredArgsConstructor
public class UpmsInnerApi_v1 implements UpmsClient {

    private final UserService userService;
    private final UserConvertor userConvertor;
    private final AccessTokenService accessTokenService;

    private final SubjectManager subjectManager;

    public Optional<Token> readToken(String token) {
        log.debug("readToken api has been invoked for {}", token);
        return accessTokenService.readToken(token).map(AccessToken::toToken);
    }

    @Override
    public Optional<UserDto> readUser(String id) {
        log.debug("readUser api has been invoked for {}", id);
        return userService.getUser(id).map(userConvertor::toDto);
    }

    @Override
    public Subject readSubject(String type, String name) {
        log.debug("readSubject api has been invoked for {}#{}", type, name);
        var principal = new Principal(type, name);
        return subjectManager.readSubject(principal);
    }

}

package com.eleven.core.security.support;

import com.eleven.core.security.SecurityService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenAuthenticationManager implements AuthenticationManager {


    private final SecurityService securityService;

    @Nonnull
    private static AnonymousAuthenticationToken createAnonymous(Authentication authentication) {
        return new AnonymousAuthenticationToken("Anonymous", authentication, List.of(new SimpleGrantedAuthority("Anonymous")));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            var authToken = (BearerTokenAuthenticationToken) authentication;

            if (StringUtils.isBlank(authToken.getToken())) {
                return createAnonymous(authentication);
            }

            var token = securityService.verifyToken(authToken.getToken()).orElse(null);

            if (null == token) {
                return createAnonymous(authentication);
            }

            var principal = token.getPrincipal();
            var subject = securityService.readSubject(principal);

            // if token is created after subject, refresh subject for new token,
            // it's almost like the user re-login.
            if (token.getCreateAt().isAfter(subject.getCreateAt())) {
                subject = securityService.createSubject(principal);
            }

            return new ElevenAuthentication(subject, principal, token);

        } catch (Exception e) {
            log.warn("认证错误", e);
            return createAnonymous(authentication);
        }
    }


}

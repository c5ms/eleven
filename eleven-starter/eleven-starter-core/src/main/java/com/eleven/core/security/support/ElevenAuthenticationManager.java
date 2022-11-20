package com.eleven.core.security.support;

import com.eleven.core.security.SecurityManager;
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


    private final SecurityManager securityManager;

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

            var token = securityManager.verifyToken(authToken.getToken()).orElse(null);
            if (null == token) {
                return createAnonymous(authentication);
            }

            // token is existing, but can not read subject for this token
            try {
                var principal = token.getPrincipal();
                var subject = securityManager.readSubject(principal);

                // if token is created after subject, refresh subject for new token,
                // it's almost like the user re-login.
                if (token.getCreateAt().isAfter(subject.getCreateAt())) {
                    subject = securityManager.createSubject(principal);
                }

                return new ElevenAuthentication(subject, principal, token);
            } catch (Exception e) {
                securityManager.invalidToken(token.getValue());
                log.warn("token 无效", e);
                return createAnonymous(authentication);
            }

        } catch (Exception e) {
            log.warn("认证错误", e);
            return createAnonymous(authentication);
        }
    }


}

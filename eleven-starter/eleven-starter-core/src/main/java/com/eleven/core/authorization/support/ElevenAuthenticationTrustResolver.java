package com.eleven.core.authorization.support;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ElevenAuthenticationTrustResolver extends AuthenticationTrustResolverImpl {
    @Override
    public boolean isAnonymous(Authentication authentication) {
        if (authentication instanceof ElevenAuthentication) {
            return ((ElevenAuthentication) authentication).getSubject().isAnonymous();
        }
        return super.isAnonymous(authentication);
    }


}

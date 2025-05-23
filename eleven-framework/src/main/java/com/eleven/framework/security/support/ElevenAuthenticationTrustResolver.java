package com.eleven.framework.security.support;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class ElevenAuthenticationTrustResolver extends AuthenticationTrustResolverImpl {
    @Override
    public boolean isAnonymous(Authentication authentication) {
        if (authentication instanceof ElevenAuthentication) {
            return ((ElevenAuthentication) authentication).getSubject().isAnonymous();
        }
        return super.isAnonymous(authentication);
    }


}

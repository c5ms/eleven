package com.demcia.eleven.core.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class PrincipalAuthenticator {
    private final Collection<PrincipalAuthenticatorProvider> principalAuthenticatorProviders;

    public Subject authenticate(Principal principal) throws AccessDeniedException {
        try {
            return principalAuthenticatorProviders.stream()
                    .filter(authenticator -> authenticator.support(principal))
                    .findFirst()
                    .map(principalAuthenticatorProvider -> principalAuthenticatorProvider.authenticate(principal))
                    .orElseThrow(() -> new AccessDeniedException("主体认证类型不支持:" + principal.getType()));
        } catch (Exception e) {
            if (e instanceof AccessDeniedException) {
                throw e;
            }
            throw new AccessDeniedException("主体认证失败", e);
        }
    }


}

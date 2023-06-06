package com.eleven.core.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 主体授权器,将认证后的主体对象进行授权。
 */
@Slf4j
@Service
@AllArgsConstructor
public class PrincipalAuthorizer {


    private final Collection<PrincipalAuthorizerProvider> principalAuthorizerProviders;

    public Collection<String> authorize(Principal principal) {
        try {
            return principalAuthorizerProviders.stream()
                    .filter(principalAuthorizerProvider -> principalAuthorizerProvider.support(principal))
                    .flatMap(authorizer -> authorizer.authorize(principal).stream())
                    .collect(Collectors.toUnmodifiableSet());
        } catch (Exception e) {
            if (e instanceof AccessDeniedException) {
                throw e;
            }
            throw new AccessDeniedException("主体授权失败", e);
        }
    }
}

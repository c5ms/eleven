package com.eleven.upms.application.support.auth;

import com.eleven.core.authorization.*;
import com.eleven.core.time.TimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocalSubjectCreator implements SubjectCreator {

    private final Collection<Authorizer> authorizers;
    private final Collection<Authenticator> authenticators;

    @Override
    public Subject createSubject(Principal principal) {
        Subject subject = authenticate(principal);
        subject.grant(new TreeSet<>(authorize(principal)));
        subject.setCreateAt(TimeHelper.localDateTime());
        return subject;
    }

    private Subject authenticate(Principal principal) throws AccessDeniedException {
        try {
            return authenticators.stream()
                .filter(authenticator -> authenticator.support(principal))
                .findFirst()
                .map(authenticator -> authenticator.authenticate(principal))
                .orElseThrow(() -> new AccessDeniedException("主体认证类型不支持:" + principal.getType()));
        } catch (Exception e) {
            if (e instanceof AccessDeniedException) {
                throw e;
            }
            throw new AccessDeniedException("主体认证失败", e);
        }
    }

    private Collection<String> authorize(Principal principal) {
        try {
            return authorizers.stream()
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

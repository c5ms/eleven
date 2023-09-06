package com.eleven.security.support;

import com.eleven.core.security.*;
import com.eleven.core.time.TimeContext;
import lombok.RequiredArgsConstructor;

import java.util.TreeSet;

@RequiredArgsConstructor
public class LocalSubjectReader implements SubjectReader {

    private final PrincipalAuthorizer principalAuthorizer;
    private final PrincipalAuthenticator principalAuthenticator;

    @Override
    public Subject readSubject(Principal principal) {
        Subject subject = principalAuthenticator.authenticate(principal);
        subject.grant(new TreeSet<>(principalAuthorizer.authorize(principal)));
        subject.setCreateAt(TimeContext.localDateTime());
        return subject;
    }
}

package com.demcia.eleven.core.security;

import com.demcia.eleven.core.time.TimeContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class SubjectManager {
    private final SubjectStore subjectStore;
    private final PrincipalAuthorizer principalAuthorizer;
    private final PrincipalAuthenticator principalAuthenticator;

    public Subject fetchSubject(Principal principal) {
        return subjectStore.retrieval(principal)
                .orElseGet(() -> createSubject(principal));
    }

    public Subject refreshSubject(Principal principal) {
        return createSubject(principal);
    }

    private Subject createSubject(Principal principal) {
        Subject subject = principalAuthenticator.authenticate(principal);
        subject.grant(new TreeSet<>(principalAuthorizer.authorize(principal)));
        subject.setCreateAt(TimeContext.localDateTime());
        subjectStore.save(principal, subject);
        return subject;
    }

}

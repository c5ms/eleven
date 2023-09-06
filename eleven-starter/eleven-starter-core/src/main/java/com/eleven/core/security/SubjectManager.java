package com.eleven.core.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectManager {

    private final SubjectStore subjectStore;
    private final SubjectReader subjectReader;

    public Subject readSubject(Principal principal) {
        var subject = subjectStore.retrieval(principal);

        if (subject.isPresent()) {
            log.debug("found subject from local store for {}", principal);
        }

        return subject.orElseGet(() -> createSubject(principal));
    }

    public Subject refreshSubject(Principal principal) {
        log.debug("refresh subject for {}", principal);
        return createSubject(principal);
    }

    private Subject createSubject(Principal principal) {
        Subject subject = subjectReader.readSubject(principal);
        subjectStore.save(principal, subject);
        log.debug("create subject for {}", principal);
        return subject;
    }

}

package com.eleven.core.security.support;

import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectStore;

import java.util.Optional;

public class EmptySubjectStore implements SubjectStore {
    @Override
    public void save(Principal principal, Subject subject) {

    }

    @Override
    public void remove(Principal principal) {

    }

    @Override
    public Optional<Subject> retrieval(Principal principal) {
        return Optional.empty();
    }
}

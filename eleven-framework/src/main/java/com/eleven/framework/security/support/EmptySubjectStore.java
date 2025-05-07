package com.eleven.framework.security.support;

import com.eleven.framework.security.Principal;
import com.eleven.framework.security.Subject;
import com.eleven.framework.security.SubjectStore;

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

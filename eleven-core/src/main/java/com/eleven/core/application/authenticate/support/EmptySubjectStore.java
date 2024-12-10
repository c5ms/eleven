package com.eleven.core.application.authenticate.support;

import com.eleven.core.application.authenticate.Principal;
import com.eleven.core.application.authenticate.Subject;
import com.eleven.core.application.authenticate.SubjectStore;

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

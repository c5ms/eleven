package com.eleven.core.auth;

import java.util.Optional;

public interface SubjectStore {

    void save(Principal principal, Subject subject);

    void remove(Principal principal);

    Optional<Subject> retrieval(Principal principal);

}

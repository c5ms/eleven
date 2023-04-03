package com.demcia.eleven.core.security;

import java.util.Optional;

public interface SubjectStore {

    void save(Principal principal, Subject subject);

    Optional<Subject> retrieval(Principal principal);

}

package com.eleven.core.auth;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

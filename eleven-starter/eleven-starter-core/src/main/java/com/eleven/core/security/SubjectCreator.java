package com.eleven.core.security;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

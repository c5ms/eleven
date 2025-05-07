package com.eleven.framework.security;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

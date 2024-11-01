package com.eleven.core.application.authentication;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

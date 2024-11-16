package com.eleven.core.application.authenticate;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

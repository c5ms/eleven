package com.eleven.core.authenticate;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

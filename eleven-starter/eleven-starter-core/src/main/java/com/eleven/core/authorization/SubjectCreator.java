package com.eleven.core.authorization;

public interface SubjectCreator {

    Subject createSubject(Principal principal);

}

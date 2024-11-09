package com.eleven.upms.api.application.support;

import com.eleven.core.authentic.Principal;
import com.eleven.core.authentic.Subject;
import com.eleven.core.authentic.SubjectCreator;
import com.eleven.upms.api.endpoint.UpmsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteSubjectCreator implements SubjectCreator {
    private final UpmsClient upmsClient;

    @Override
    public Subject createSubject(Principal principal) {
        return upmsClient.createSubject(principal.getType(), principal.getName());
    }

}

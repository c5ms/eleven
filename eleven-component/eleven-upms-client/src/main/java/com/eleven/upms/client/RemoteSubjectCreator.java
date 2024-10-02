package com.eleven.upms.client;

import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectCreator;
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

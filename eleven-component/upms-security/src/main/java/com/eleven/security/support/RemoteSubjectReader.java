package com.eleven.security.support;

import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.core.security.SubjectReader;
import com.eleven.upms.client.UpmsClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoteSubjectReader implements SubjectReader {
    private final UpmsClient upmsClient;

    @Override
    public Subject readSubject(Principal principal) {
        return upmsClient.readSubject(principal.getType(), principal.getName());
    }
}

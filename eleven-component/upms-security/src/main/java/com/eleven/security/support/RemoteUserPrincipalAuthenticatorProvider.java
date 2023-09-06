package com.eleven.security.support;

import com.eleven.core.security.Principal;
import com.eleven.core.security.PrincipalAuthenticatorProvider;
import com.eleven.core.security.Subject;
import com.eleven.upms.client.UpmsClient;
import com.eleven.upms.core.UpmsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashSet;

@RequiredArgsConstructor
public class RemoteUserPrincipalAuthenticatorProvider implements PrincipalAuthenticatorProvider {
    private final UpmsClient upmsClient;

    @Override
    public boolean support(Principal principal) {
        return UpmsConstants.PRINCIPAL_TYPE_USER.equals(principal.getType());
    }

    @Override
    public Subject authenticate(Principal principal) throws AccessDeniedException {
        var user = upmsClient.readUser(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("用户不存在"));
        return new Subject(user.getId(), user.getNickname(), principal, new HashSet<>());
    }

}

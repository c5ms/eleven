package com.demcia.eleven.upms.application.support;

import com.demcia.eleven.core.security.Principal;
import com.demcia.eleven.core.security.PrincipalAuthenticatorProvider;
import com.demcia.eleven.core.security.Subject;
import com.demcia.eleven.upms.application.client.UpmsClient;
import com.demcia.eleven.upms.application.core.constants.UpmsConstants;
import com.demcia.eleven.upms.application.strategy.ConditionalOnUseRemoteAuthenticate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@ConditionalOnUseRemoteAuthenticate
@RequiredArgsConstructor
public class RemoteUserPrincipalAuthenticatorProvider implements PrincipalAuthenticatorProvider {
    private final UpmsClient upmsClient;

    @Override
    public boolean support(Principal principal) {
        return UpmsConstants.LOCAL_USER_PRINCIPAL_TYPE.equals(principal.getType());
    }

    @Override
    public Subject authenticate(Principal principal) throws AccessDeniedException {
        var user = upmsClient.getUser(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("用户不存在"));
        return new Subject(user.getId(), user.getNickname(), principal, new HashSet<>());
    }
}

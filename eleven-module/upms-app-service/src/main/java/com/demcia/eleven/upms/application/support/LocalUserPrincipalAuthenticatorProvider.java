package com.demcia.eleven.upms.application.support;

import com.demcia.eleven.core.security.Principal;
import com.demcia.eleven.core.security.PrincipalAuthenticatorProvider;
import com.demcia.eleven.core.security.Subject;
import com.demcia.eleven.upms.application.core.constants.UpmsConstants;
import com.demcia.eleven.upms.application.strategy.ConditionalOnUseLocalAuthenticate;
import com.demcia.eleven.upms.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Primary
@Service
@ConditionalOnUseLocalAuthenticate
@RequiredArgsConstructor
public class LocalUserPrincipalAuthenticatorProvider implements PrincipalAuthenticatorProvider {
    private final UserService userService;

    @Override
    public boolean support(Principal principal) {
        return UpmsConstants.LOCAL_USER_PRINCIPAL_TYPE.equals(principal.getType());
    }

    @Override
    public Subject authenticate(Principal principal) throws AccessDeniedException {
        var user = userService.getUser(principal.getName())
                .orElseThrow(() -> new AccessDeniedException("用户不存在"));
        return new Subject(user.getId(), user.getNickname(), principal, new HashSet<>());
    }
}

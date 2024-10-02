package com.eleven.upms.application.support;

import com.eleven.core.security.Authenticator;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Subject;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserAuthenticator implements Authenticator {
    private final UserRepository userRepository;

    @Override
    public boolean support(Principal principal) {
        return UpmsConstants.PRINCIPAL_TYPE_USER.equals(principal.getType());
    }

    @Override
    public Subject authenticate(Principal principal) throws AccessDeniedException {
        var user = userRepository.findById(principal.getName())
            .filter(find -> !find.isDeleted())
            .orElseThrow(() -> new AccessDeniedException("用户不存在"));
        return new Subject(user.getId(), user.getNickname(), principal, new HashSet<>());
    }

}

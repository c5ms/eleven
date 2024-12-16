package com.eleven.domain.user.support.auth;

import com.eleven.domain.user.model.User;
import com.eleven.domain.user.model.UserRepository;
import com.eleven.framework.authentic.Authenticator;
import com.eleven.framework.authentic.Principal;
import com.eleven.framework.authentic.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashSet;

@RequiredArgsConstructor
public class UserAuthenticator implements Authenticator {
    private final UserRepository userRepository;

    @Override
    public boolean support(Principal principal) {
        return User.PRINCIPAL_TYPE_USER.equals(principal.getType());
    }

    @Override
    public Subject authenticate(Principal principal) throws AccessDeniedException {
        var user = userRepository.findById(principal.getName())
                .filter(find -> !find.isDeleted())
                .orElseThrow(() -> new AccessDeniedException("用户不存在"));
        return new Subject(user.getId(), user.getUsername(), principal, new HashSet<>());
    }

}

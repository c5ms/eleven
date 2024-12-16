package com.eleven.domain.user;

import com.eleven.domain.user.manager.UserManager;
import com.eleven.domain.user.model.AccessToken;
import com.eleven.domain.user.model.AccessTokenRepository;
import com.eleven.domain.user.model.User;
import com.eleven.framework.authentic.SecurityManager;
import com.eleven.upms.api.domain.core.UpmsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserManager userManager;
    private final SecurityManager securityManager;

    private final AccessTokenRepository accessTokenRepository;

    @Transactional(rollbackFor = Exception.class)
    public Token login(Principal principal, String credential, TokenDetail detail) {
        if (principal.getType().equals(User.PRINCIPAL_TYPE_USER)) {
            principal = userManager.login(principal.getName(), credential);
        } else {
            throw UpmsConstants.ERROR_UNSUPPORTED_IDENTITY.toException();
        }
        var token = securityManager.createToken(principal, detail);

        var accessToken = new AccessToken(token);
        accessTokenRepository.save(accessToken);
        return token;
    }

    public Optional<Subject> getSubject(String token) {
        return securityManager.verifyToken(token)
                .map(Token::getPrincipal)
                .map(securityManager::readSubject);
    }

    public Subject createSubject(String type, String name) {
        var principal = new Principal(type, name);
        return securityManager.createSubject(principal);
    }

    public void deleteToken(String token) {
        securityManager.invalidToken(token);
        accessTokenRepository.deleteById(token);
    }

    public Optional<Token> readToken(String token) {
        return securityManager.verifyToken(token);
    }
}

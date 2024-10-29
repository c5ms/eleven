package com.eleven.upms.application.service;

import com.eleven.core.auth.*;
import com.eleven.core.auth.SecurityManager;
import com.eleven.upms.api.domain.core.UpmsConstants;
import com.eleven.upms.domain.manager.UserManager;
import com.eleven.upms.domain.model.AccessToken;
import com.eleven.upms.domain.model.AccessTokenRepository;
import com.eleven.upms.domain.model.User;
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

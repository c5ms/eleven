package com.eleven.upms.application.service;

import com.eleven.core.security.SecurityManager;
import com.eleven.core.security.*;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.domain.service.AccessTokenManager;
import com.eleven.upms.domain.service.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserManager userManager;
    private final SecurityManager securityManager;
    private final AccessTokenManager accessTokenManager;

    @Transactional(rollbackFor = Exception.class)
    public Token login(Principal principal, String credential, TokenDetail detail) {
        if (principal.getType().equals(UpmsConstants.PRINCIPAL_TYPE_USER)) {
            principal = userManager.login(principal.getName(), credential);
        } else {
            throw UpmsConstants.ERROR_UNSUPPORTED_IDENTITY.exception();
        }
        var token = securityManager.createToken(principal, detail);
        accessTokenManager.saveToken(token);
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
        accessTokenManager.deleteToken(token);
    }


    public Optional<Token> readToken(String token) {
        return securityManager.verifyToken(token);
    }
}

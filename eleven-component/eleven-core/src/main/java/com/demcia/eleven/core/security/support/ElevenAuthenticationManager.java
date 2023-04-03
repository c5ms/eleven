package com.demcia.eleven.core.security.support;

import com.demcia.eleven.core.security.SubjectManager;
import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.core.security.TokenReader;
import com.demcia.eleven.core.security.TokenStore;
import com.demcia.eleven.core.time.TimeContext;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElevenAuthenticationManager implements AuthenticationManager {

    private final TokenReader tokenReader;
    private final TokenStore tokenStore;
    private final SubjectManager subjectManager;

    @NotNull
    private static AnonymousAuthenticationToken createAnonymous(Authentication authentication) {
        return new AnonymousAuthenticationToken("Anonymous", authentication, List.of(new SimpleGrantedAuthority("Anonymous")));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            var authToken = (BearerTokenAuthenticationToken) authentication;
            if (StringUtils.isBlank(authToken.getToken())) {
                return createAnonymous(authentication);
            }

            // 本地缓存
            var existToken = tokenStore.retrieval(authToken.getToken());

            // 本地缓存过期
            if (existToken.isPresent() && existToken.get().getExpireAt().isBefore(TimeContext.localDateTime())) {
                existToken = tokenReader.read(authToken.getToken());
                existToken.ifPresent(tokenStore::save);
            }

            // 本地没有缓存
            if (existToken.isEmpty()) {
                existToken = tokenReader.read(authToken.getToken());
                existToken.ifPresent(tokenStore::save);
            }

            // 无有效 token
            if (existToken.isEmpty()) {
                return createAnonymous(authentication);
            }

            // 过期 -> 匿名
            if (existToken.get().getExpireAt().isBefore(TimeContext.localDateTime())) {
                return createAnonymous(authentication);
            }

            return createAuthentication(existToken.get());
        } catch (Exception e) {
            log.warn("认证错误", e);
            return createAnonymous(authentication);
        }
    }

    @NotNull
    public ElevenAuthentication createAuthentication(Token token) {
        var principal = token.getPrincipal();
        var subject = subjectManager.fetchSubject(principal);
        if (token.getCreateAt().isAfter(subject.getCreateAt())) {
            subject = subjectManager.refreshSubject(principal);
        }
        return new ElevenAuthentication(subject, principal, token);
    }


}

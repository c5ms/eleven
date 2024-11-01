package com.eleven.core.application.authentication;

import com.eleven.core.time.TimeContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticSupport {

    private final TokenCreator tokenCreator;
    private final SubjectCreator subjectCreator;
    private final TokenStore tokenStore;
    private final SubjectStore subjectStore;

    public Subject readSubject(Principal principal) {
        var subject = subjectStore.retrieval(principal);
        subject.ifPresent(hit -> log.debug("hit existing Subject for {}, {}", hit.getPrincipal().identity(), hit.getNickName()));
        return subject.orElseGet(() -> {
            var newSubject = createSubject(principal);
            subjectStore.save(principal, newSubject);
            return newSubject;
        });
    }

    public Subject createSubject(Principal principal) {
        var subject = subjectCreator.createSubject(principal);
        log.debug("create new subject for {}", principal.identity());
        return subject;
    }

    public Token createToken(Principal principal, TokenDetail detail) {
        var token = tokenCreator.create(principal, detail);
        log.debug("create new Token for {}", principal.identity());
        tokenStore.save(token);
        return token;
    }

    /**
     * verify a token , fetch real token object from token store
     *
     * @param value the token pure value
     * @return the token if it is existing, or empty
     */
    public Optional<Token> verifyToken(String value) {
        var token = tokenStore.retrieval(value);
        token.ifPresent(hit -> log.debug("hit existing Token for {}", hit.getPrincipal().identity()));
        return token.filter(exist -> exist.getExpireAt().isAfter(TimeContext.localDateTime()));
    }

    /**
     * invalid a token
     *
     * @param value token value
     */
    public void invalidToken(String value) {
        tokenStore.remove(value);
    }

}

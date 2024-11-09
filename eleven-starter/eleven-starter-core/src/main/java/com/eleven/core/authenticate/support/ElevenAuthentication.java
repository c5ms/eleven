package com.eleven.core.authenticate.support;

import com.eleven.core.authenticate.Principal;
import com.eleven.core.authenticate.Subject;
import com.eleven.core.authenticate.Token;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.util.stream.Collectors;

@Getter
@Setter
public class ElevenAuthentication extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Token token;
    private final Subject subject;
    private final Principal principal;

    public ElevenAuthentication(Subject subject, Principal principal, Token token) {
        super(subject.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        this.subject = subject;
        this.token = token;
        this.principal = principal;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Token getCredentials() {
        return token;
    }

    @Override
    public Subject getDetails() {
        return subject;
    }

}

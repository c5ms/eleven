package com.eleven.core.domain;

import com.eleven.core.security.Principal;
import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultAuditorAware implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        if(SecurityContext.isAnonymous()){
            return Optional.of(Subject.ANONYMOUS_INSTANCE.getUserId());
        }
        return Optional.of(SecurityContext.getCurrentSubject())
                .map(Subject::getPrincipal)
                .map(Principal::identify);
    }

}

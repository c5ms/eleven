package com.eleven.core.data;

import com.eleven.core.security.Principal;
import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
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
                .map(Principal::identity);
    }

}

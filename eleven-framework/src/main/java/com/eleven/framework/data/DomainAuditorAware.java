package com.eleven.framework.data;

import com.eleven.framework.security.AuthenticContext;
import com.eleven.framework.security.Principal;
import com.eleven.framework.security.Subject;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class DomainAuditorAware implements AuditorAware<String>, DateTimeProvider {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        if (AuthenticContext.isAnonymous()) {
            return Optional.of(Subject.ANONYMOUS_INSTANCE.getUserId());
        }
        return Optional.of(AuthenticContext.getCurrentSubject())
                .map(Subject::getPrincipal)
                .map(Principal::identity);
    }

    @Override
    @Nonnull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDate.now());
    }

}

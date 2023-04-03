package com.demcia.eleven.security.domain;

import com.demcia.eleven.core.security.SecurityUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnClass(SecurityUtil.class)
public class ElevenEntityAuditor implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtil.getCurrentSubject().getUserId());
    }

}

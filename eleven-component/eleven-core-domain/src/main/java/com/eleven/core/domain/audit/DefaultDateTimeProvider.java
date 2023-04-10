package com.eleven.core.domain.audit;

import com.eleven.core.time.TimeContext;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class DefaultDateTimeProvider implements DateTimeProvider {
    @Override
    @Nonnull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(TimeContext.localDateTime());
    }
}

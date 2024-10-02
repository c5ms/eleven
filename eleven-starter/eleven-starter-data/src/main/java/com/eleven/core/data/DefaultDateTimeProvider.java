package com.eleven.core.data;

import com.eleven.core.time.TimeContext;
import jakarta.annotation.Nonnull;
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

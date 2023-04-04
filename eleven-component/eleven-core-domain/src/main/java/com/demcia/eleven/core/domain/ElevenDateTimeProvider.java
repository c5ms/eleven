package com.demcia.eleven.core.domain;

import com.demcia.eleven.core.time.TimeContext;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class ElevenDateTimeProvider implements DateTimeProvider {
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(TimeContext.localDateTime());
    }
}

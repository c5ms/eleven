package com.demcia.eleven.core.domain.time;

import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@RequiredArgsConstructor
public class DefaultTimeService implements TimeService {

    private final Clock clock;

    @Override
    public ZoneId getZoneId() {
        return clock.getZone();
    }

    @Override
    public Instant getInstant() {
        return clock.instant();
    }
}

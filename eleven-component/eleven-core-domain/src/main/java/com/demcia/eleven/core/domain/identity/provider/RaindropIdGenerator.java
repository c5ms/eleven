package com.demcia.eleven.core.domain.identity.provider;

import com.demcia.eleven.core.domain.identity.IdGenerator;
import com.demcia.eleven.core.domain.time.TimeContext;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RaindropIdGenerator implements IdGenerator {

    private final String prefix;
    private final Map<String, AtomicLong> seqMap = new ConcurrentHashMap<>();

    public RaindropIdGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public synchronized String nextId(String schema) {
        AtomicLong seq = seqMap.computeIfAbsent(schema, s -> new AtomicLong());
        LocalDateTime localDate = LocalDateTime.now(TimeContext.getClock());
        return String.format("%s%d%02d%02d%02d%02d%02d%06d",
                prefix,
                localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getHour(),
                localDate.getMinute(),
                localDate.getSecond(),
                seq.incrementAndGet()
        );
    }

}

package com.eleven.core.generate.support;

import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.time.TimeContext;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class RaindropGenerator implements IdentityGenerator {

    private final AtomicLong seq = new AtomicLong();
    private final long workerId;
    private final long datacenterId;

    public RaindropGenerator(long datacenterId,long workerId) {
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    @Override
    public synchronized String next() {
        LocalDateTime localDate = LocalDateTime.now(TimeContext.getClock());
        return String.format("%d%03d%02d%02d%02d%02d%02d%02d%06d",
                datacenterId,
                workerId,
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

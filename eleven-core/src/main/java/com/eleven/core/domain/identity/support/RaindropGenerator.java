package com.eleven.core.domain.identity.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.domain.identity.IdentityGenerator;

import java.time.Clock;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public class RaindropGenerator implements IdentityGenerator {

    private final AtomicLong seq = new AtomicLong();
    private final long workerId;
    private final long datacenterId;

    public RaindropGenerator() {
        var datacenterId = IdUtil.getDataCenterId(99);
        var workerId = IdUtil.getWorkerId(datacenterId, 99);
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    @Override
    public synchronized String next() {
        var timestampSec = Clock.systemUTC().millis() / 1000;
        return String.format("%s%06d", Long.toHexString(timestampSec * 10000 + datacenterId * 100 + workerId), seq.incrementAndGet()
        ).toUpperCase(Locale.ROOT);
    }

}

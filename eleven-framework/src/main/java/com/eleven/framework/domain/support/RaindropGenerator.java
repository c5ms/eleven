package com.eleven.framework.domain.support;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import cn.hutool.core.util.IdUtil;
import com.eleven.framework.domain.IdentityGenerator;

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
    public String next() {
        var timestampSec = Clock.systemUTC().millis() / 1000;
        var d = timestampSec * 10000 + datacenterId * 100 + workerId;
        var i = seq.incrementAndGet();
        var s = String.format("%s%06d", Long.toHexString(d), i);
        return s.toUpperCase(Locale.ROOT);
    }

}

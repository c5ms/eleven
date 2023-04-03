package com.demcia.eleven.core.generate.support;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.core.generate.IdentityGenerator;
import com.demcia.eleven.core.time.TimeContext;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class RaindropGenerator implements IdentityGenerator {

    private final AtomicLong seq = new AtomicLong();
    private final long workId;
    private final long datacenterId;

    public RaindropGenerator(long datacenterId) {
        this.datacenterId = datacenterId;
        this.workId = IdUtil.getWorkerId(datacenterId, 1024);
    }

    @Override
    public synchronized String next() {
        LocalDateTime localDate = LocalDateTime.now(TimeContext.getClock());
        return String.format("%d%03d%02d%02d%02d%02d%02d%02d%06d",
                datacenterId,
                workId,
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

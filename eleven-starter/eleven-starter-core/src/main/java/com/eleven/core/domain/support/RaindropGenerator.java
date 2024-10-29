package com.eleven.core.domain.support;

import cn.hutool.core.util.IdUtil;
import com.eleven.core.domain.IdentityGenerator;
import com.eleven.core.time.TimeHelper;

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
//        LocalDateTime localDate = LocalDateTime.now(TimeContext.getClock());
//        return String.format("%02d%02d%02d%02d%02d%02d%02d%02d%06d",
//            datacenterId,
//            workerId,
//            localDate.getYear(),
//            localDate.getMonthValue(),
//            localDate.getDayOfMonth(),
//            localDate.getHour(),
//            localDate.getMinute(),
//            localDate.getSecond(),
//            seq.incrementAndGet()
//        );
        return String.format("%s%s%06d",
            Long.toHexString(TimeHelper.getClock().millis()/1000),
            Long.toHexString(datacenterId * 100 + workerId),
            seq.incrementAndGet()
        ).toUpperCase(Locale.ROOT);
    }

}

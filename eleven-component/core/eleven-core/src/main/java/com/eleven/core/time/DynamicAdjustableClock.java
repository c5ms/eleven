package com.eleven.core.time;

import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Slf4j
public class DynamicAdjustableClock extends Clock {

    private final ZoneId zone;
    private final long expiration;
    private final TimestampProvider provider;

    private volatile long baseMilli;
    private volatile long adjustingTimeMilli = 0;

    public DynamicAdjustableClock(TimestampProvider provider,
                                  long expiration,
                                  ZoneId zone) {
        this.expiration = expiration;
        this.zone = zone;
        this.provider = provider;
    }

    /**
     * 获取时钟使用时长
     *
     * @return 使用时长毫秒数
     */
    public long getUsedTime() {
        return System.currentTimeMillis() - adjustingTimeMilli;
    }

    /**
     * 时钟是否过期
     *
     * @return true 表示已经过期
     */
    public boolean isExpired() {
        return this.getUsedTime() >= expiration;
    }


    /**
     * 时钟矫正
     */
    private void adjusting() {
        long start = System.currentTimeMillis();
        this.baseMilli = provider.provide() + (System.currentTimeMillis() - start);
        this.adjustingTimeMilli = System.currentTimeMillis();
    }


    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(this.zone)) {
            return this;
        }
        DynamicAdjustableClock newClock = new DynamicAdjustableClock(provider, expiration, zone);
        newClock.baseMilli = this.baseMilli;
        newClock.adjustingTimeMilli = this.adjustingTimeMilli;
        return newClock;
    }

    @Override
    public Instant instant() {
        if (isExpired()) {
            synchronized (this) {
                if (isExpired()) {
                    adjusting();
                }
            }
        }
        long used = getUsedTime();
        long ms = baseMilli + used;
        return Instant.ofEpochMilli(ms);
    }
}

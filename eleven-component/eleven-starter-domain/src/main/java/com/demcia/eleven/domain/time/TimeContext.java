package com.demcia.eleven.domain.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间服务上下文，确保了进入spring上下文的线程会持有一个系统自己实现的时钟服务
 */
public final class TimeContext {

    private static Clock clock = Clock.systemDefaultZone();

    private TimeContext() {

    }

    public static Clock getClock() {
        return clock;
    }

    /**
     * 根据当前系统时钟服务，创建当前系统时间
     *
     * @return 当前系统时间
     */
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

    /**
     * 根据当前系统时钟服务，创建当前系统时间
     *
     * @return 当前系统时间
     */
    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    /**
     * 根据当前系统时钟服务，创建当前系统时间
     *
     * @return 当前系统时间
     */
    public static LocalTime localTime() {
        return LocalTime.now(clock);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration(proxyBeanMethods = false)
    public static class DataTimeContextAutoconfigure {

        @Autowired
        public DataTimeContextAutoconfigure(DynamicAdjustableClock clock) {
            TimeContext.clock = clock;
        }

    }

}

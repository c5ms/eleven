package com.eleven.core.time;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.*;

/**
 * 时间服务上下文，确保了进入spring上下文的线程会持有一个系统自己实现的时钟服务
 */
@UtilityClass
public final class TimeContext {

    @Getter
    static Clock clock = Clock.systemDefaultZone();

    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public static LocalTime localTime() {
        return LocalTime.now(clock);
    }

    public  static  long millis(){
        return clock.millis();
    }

    public static Instant instant() {
        return Instant.now(getClock());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration(proxyBeanMethods = false)
    public static class TimeHelperAutoconfigure {
        @Autowired
        public TimeHelperAutoconfigure(DynamicAdjustableClock clock) {
            TimeContext.clock = clock;
        }
    }

}

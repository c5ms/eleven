package com.eleven.core.time.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.ZoneId;

@Data
@ConfigurationProperties(prefix = "eleven.time")
public class DateTimeProperties {

    /**
     * 时间提供服务
     */
    private Provider provider = Provider.LOCAL_CLOCK;

    /**
     * 时钟过期时间，默认是 2 分钟
     */
    private Duration clockExpiration = Duration.ofMinutes(2);

    /**
     * 时区
     */
    private ZoneId zoneId = ZoneId.systemDefault();


    public enum Provider {
        /**
         * 由数据库提供当前时间
         */
        DATE_BASE,
        /**
         * 由本地系统时钟提供
         */
        LOCAL_CLOCK,
    }


}

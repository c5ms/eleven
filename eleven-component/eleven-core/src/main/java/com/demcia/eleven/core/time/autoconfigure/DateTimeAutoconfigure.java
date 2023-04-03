package com.demcia.eleven.core.time.autoconfigure;

import com.demcia.eleven.core.time.support.DataBaseTimestampProvider;
import com.demcia.eleven.core.time.DynamicAdjustableClock;
import com.demcia.eleven.core.time.TimestampProvider;
import com.demcia.eleven.core.time.support.LocalTimestampProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(DateTimeProperties.class)
public class DateTimeAutoconfigure {

    private final DateTimeProperties properties;

    @Bean
    public TimestampProvider nowProvider(DataSource dataSource) {
        switch (properties.getProvider()) {
            case DATE_BASE:
                return new DataBaseTimestampProvider(dataSource);
            case LOCAL_CLOCK:
            default:
                return new LocalTimestampProvider();
        }
    }

    @Bean
    public DynamicAdjustableClock clock(TimestampProvider provider) {
        return new DynamicAdjustableClock(provider,
                properties.getClockExpiration().toMillis(),
                properties.getZoneId());
    }


}

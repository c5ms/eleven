package com.eleven.core.time.autoconfigure;

import com.eleven.core.time.DynamicAdjustableClock;
import com.eleven.core.time.TimestampProvider;
import com.eleven.core.time.support.DataBaseTimestampProvider;
import com.eleven.core.time.support.LocalTimestampProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
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
    public TimestampProvider nowProvider(ListableBeanFactory listableBeanFactory) {
        switch (properties.getProvider()) {
            case DATE_BASE -> {
                var dataSource = listableBeanFactory.getBean(DataSource.class);
                return new DataBaseTimestampProvider(dataSource);
            }
            default -> {
                return new LocalTimestampProvider();
            }
        }
    }

    @Bean
    public DynamicAdjustableClock clock(TimestampProvider provider) {
        return new DynamicAdjustableClock(provider,
            properties.getClockExpiration().toMillis(),
            properties.getZoneId());
    }


}

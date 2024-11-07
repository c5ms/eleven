package com.eleven.core.configure;

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
import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(DateTimeProperties.class)
public class DateTimeAutoconfigure {

    private final DateTimeProperties properties;

    @Bean
    public TimestampProvider nowProvider(ListableBeanFactory listableBeanFactory) {
        if (Objects.requireNonNull(properties.getProvider()) == DateTimeProperties.Provider.DATE_BASE) {
            var dataSource = listableBeanFactory.getBean(DataSource.class);
            return new DataBaseTimestampProvider(dataSource);
        }
        return new LocalTimestampProvider();
    }

    @Bean
    public DynamicAdjustableClock clock(TimestampProvider provider) {
        return new DynamicAdjustableClock(provider,
            properties.getClockExpiration().toMillis(),
            properties.getZoneId());
    }


}

package com.eleven.gateway.management.config;

import com.eleven.core.data.support.ListValueWriteConverter;
import com.eleven.core.data.support.ListValueReadConverter;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class GatewayAdminConfig extends AbstractJdbcConfiguration {

    @Override
    @Nonnull
    protected List<?> userConverters() {
        return List.of(
            new ListValueWriteConverter(),
            new ListValueReadConverter()
        );
    }
}

package com.eleven.core.log.autoconfigure;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import ch.qos.logback.core.util.Duration;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(LogstashProperties.class)
public class LogstashAutoconfigure {

    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";

    @Bean
    @ConditionalOnProperty(prefix = "eleven.logs.logstash", value = "enabled", havingValue = "true")
    public LogstashTcpSocketAppender logstashAppender(LogstashProperties logstashProperties) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName(LOGSTASH_APPENDER_NAME);
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(logstashProperties.getUrl());
        logstashTcpSocketAppender.setReconnectionDelay(Duration.buildByMilliseconds(1000));
        if (logstashProperties.getTrustStoreLocation() != null) {
            SSLConfiguration sslConfiguration = new SSLConfiguration();
            KeyStoreFactoryBean factory = new KeyStoreFactoryBean();
            factory.setLocation(logstashProperties.getTrustStoreLocation());
            if (logstashProperties.getTrustStorePassword() != null)
                factory.setPassword(logstashProperties.getTrustStorePassword());
            sslConfiguration.setTrustStore(factory);
            logstashTcpSocketAppender.setSsl(sslConfiguration);
        }
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setTimeZone(TimeZone.getDefault().getID());
        encoder.setEncoding(StandardCharsets.UTF_8.name());
        encoder.addProvider(new AbstractFieldJsonProvider<>() {
            @Override
            public void writeTo(JsonGenerator generator, ILoggingEvent iLoggingEvent) throws IOException {
                generator.writeStringField("service_name", SpringUtil.getApplicationName());
            }
        });
        encoder.start();
        logstashTcpSocketAppender.setEncoder(encoder);
        logstashTcpSocketAppender.start();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(logstashTcpSocketAppender);
        return logstashTcpSocketAppender;
    }

}

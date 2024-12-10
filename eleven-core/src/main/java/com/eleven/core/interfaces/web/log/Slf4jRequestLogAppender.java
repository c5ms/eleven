package com.eleven.core.interfaces.web.log;

import com.eleven.core.application.authenticate.AuthenticContext;
import com.fasterxml.jackson.core.JsonGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.marker.LogstashMarker;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Slf4jRequestLogAppender implements RequestLogAppender {
    private static final org.slf4j.Logger requestLogger = LoggerFactory.getLogger("request");

    @PostConstruct
    public void init() {
    }

    @Override
    public void append(RequestLog requestLog) {
        if (StringUtils.isBlank(requestLog.getOperation())) {
            return;
        }
        var subject = AuthenticContext.getCurrentSubject();
        requestLog.setUserId(subject.getUserId());
        var exception = RequestLogContext.getCurrentError();
        var marker = new LogstashMarker("requestLog") {
            @Override
            public void writeTo(JsonGenerator generator) throws IOException {
                generator.writePOJOField("request", requestLog.getRequest());
                generator.writePOJOField("response", requestLog.getResponse());
                generator.writeNumberField("duration", requestLog.getDuration());
                generator.writeStringField("operate", requestLog.getOperation());
                generator.writeStringField("client_ip", requestLog.getClientIp());
                generator.writeStringField("user_id", requestLog.getUserId());
                generator.writePOJOField("principal", subject.getPrincipal());
            }
        };

        // 找到合适的 logger
        var logger = requestLogger;
        if (null != RequestLogContext.getHandler()) {
            logger = LoggerFactory.getLogger(RequestLogContext.getHandler());
        }

        var status = HttpStatus.valueOf(requestLog.getResponse().getStatus());

        if (status.is5xxServerError()) {
            if (logger.isErrorEnabled()) {
                if (exception.isPresent()) {
                    logger.error(marker, "response http status {} when {} executed {} occur exception",
                            status.value(),
                            subject.getNickName(),
                            requestLog.getOperation(),
                            exception.get());
                    return;
                }
            }
        }

        if (status.is4xxClientError()) {
            if (logger.isWarnEnabled()) {
                if (exception.isPresent()) {
                    logger.warn(marker, "response http status {} when {} executed {} fail, because {}",
                            status.value(),
                            subject.getNickName(),
                            requestLog.getOperation(),
                            ExceptionUtils.getRootCauseMessage(exception.get()));
                    return;
                }
                logger.warn(marker, "response http status {} when {} executed {} fail with no reason",
                        status.value(),
                        subject.getNickName(),
                        requestLog.getOperation()
                );
                return;
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info(marker, "response http status {} when {} executed {} successful",
                    status.value(),
                    subject.getNickName(),
                    requestLog.getOperation());
        }

    }
}

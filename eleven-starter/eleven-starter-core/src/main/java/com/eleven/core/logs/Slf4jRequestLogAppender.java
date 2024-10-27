package com.eleven.core.logs;

import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import com.fasterxml.jackson.core.JsonGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.marker.LogstashMarker;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Slf4jRequestLogAppender implements RequestLogAppender {
    private static final org.slf4j.Logger requestLogger = LoggerFactory.getLogger("request");

    @PostConstruct
    public void init() {
    }

    @Override
    public void append(RequestLog requestLog) {
        Subject subject = SecurityContext.getCurrentSubject();
        requestLog.setUserId(subject.getUserId());
        Optional<Throwable> exception = RequestLogContext.getCurrentError();
        LogstashMarker marker = new LogstashMarker("requestLog") {
            @Override
            public void writeTo(JsonGenerator generator) throws IOException {
                generator.writePOJOField("request", requestLog.getRequest());
                generator.writePOJOField("response", requestLog.getResponse());
                generator.writeNumberField("duration", requestLog.getDuration());
                generator.writeStringField("operate", requestLog.getOperate());
                generator.writeStringField("client_ip", requestLog.getClientIp());
                generator.writeStringField("user_id", requestLog.getUserId());
                generator.writePOJOField("principal", subject.getPrincipal());
            }
        };

        // 找到合适的 logger
        org.slf4j.Logger logger = requestLogger;
        if (null != RequestLogContext.getHandler()) {
            logger = LoggerFactory.getLogger(RequestLogContext.getHandler());
        }

        var status = HttpStatus.valueOf(requestLog.getResponse().getStatus());

        if (status.is5xxServerError()) {
            if (logger.isErrorEnabled()) {
                if (exception.isPresent()) {
                    logger.error(marker, "【{}】执行【{}】处理错误 : {}", subject.getNickName(), requestLog.getOperate(), ExceptionUtils.getRootCauseMessage(exception.get()), exception.get());
                    return;
                }
            }
        }

        if (status.is4xxClientError()) {
            if (logger.isDebugEnabled()) {
                if (exception.isPresent()) {
                    logger.warn(marker, "【{}】执行【{}】处理失败 : {}", subject.getNickName(), requestLog.getOperate(), ExceptionUtils.getRootCauseMessage(exception.get()),exception.get());
                    return;
                }
            }

            if (logger.isWarnEnabled()) {
                if (exception.isPresent()) {
                    logger.warn(marker, "【{}】执行【{}】处理失败 : {}", subject.getNickName(), requestLog.getOperate(), ExceptionUtils.getRootCauseMessage(exception.get()));
                    return;
                }
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info(marker, "【{}】执行【{}】处理成功", subject.getNickName(), requestLog.getOperate());
        }

    }
}

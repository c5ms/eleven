package com.eleven.core.logs;

import com.eleven.core.security.SecurityContext;
import com.eleven.core.security.Subject;
import com.fasterxml.jackson.core.JsonGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.marker.LogstashMarker;
import org.slf4j.LoggerFactory;
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

        // error 的时候 一定输出日志
        if (exception.isPresent()) {
            logger.error(marker, "【{}】执行【{}】处理错误", subject.getNickName(), requestLog.getOperate(), exception.get());
            return;
        }


        // 要求持久化的时候，使用 info 输出
        if (RequestLogContext.isDurable()) {
            logger.info(marker, "【{}】执行【{}】处理成功", subject.getNickName(), requestLog.getOperate());
        }

        // 否则根据是否开启了 trace 级别打印
        else if (logger.isTraceEnabled()) {
            // trace 模式全部打印
            logger.trace(marker, "【{}】执行【{}】处理成功", subject.getNickName(), requestLog.getOperate());
        }
    }
}

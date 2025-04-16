package com.eleven.framework.web.log;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RequestLog implements Serializable {

    private String userId;
    private String traceId;
    private String operation;
    private Long duration;
    private String clientIp;
    private String serverIp;
    private String serviceName;
    private Exception exception;
    private LocalDateTime requestTime;
    private LocalDateTime responseTime;
    private Request request = new Request();
    private Response response = new Response();

    @Data
    @Accessors(chain = true)
    public static class Exception implements Serializable {
        private String traceId;
        private String exceptionClass;
        private String rootCauseClass;
        private String rootCauseMessage;
        private String exceptionMessage;
        private String exceptionStack;
        private LocalDateTime happenTime;
        private String declaringClass;
        private String methodName;
        private String fileName;
        private Integer lineNumber;
    }

    @Data
    @Accessors(chain = true)
    public static class Request implements Serializable {
        private String path;
        private String url;
        private String method;
        private String contentType;
        private String parameter;
        private String body;
        private boolean isUpload;
    }

    @Data
    @Accessors(chain = true)
    public static class Response implements Serializable {
        private Integer status;
        private String body;
        private String contentType;
    }
}

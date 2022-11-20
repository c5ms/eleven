package com.eleven.core.logs;

public interface RequestLogAppender {
    /**
     * 写入请求日志
     *
     * @param requestLog 请求日志
     */
    void append(RequestLog requestLog);
}

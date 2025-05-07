package com.eleven.framework.log;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Optional;

public final class RequestLogContext {

    private static final ThreadLocal<RequestLog> logHolder = ThreadLocal.withInitial(RequestLog::new);
    private static final ThreadLocal<Class<?>> handlerHolder = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Boolean> durableHolder = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Throwable> exceptionHolder = ThreadLocal.withInitial(() -> null);

    private RequestLogContext() {

    }

    /**
     * 标记日志需要存储
     */
    public static void markPersist() {
        durableHolder.set(true);
    }

    /**
     * 标记不需要存储
     */
    public static void markUnPersist() {
        durableHolder.set(false);
    }

    /**
     * 是否需要存储
     */
    public static boolean isDurable() {
        return BooleanUtils.isTrue(durableHolder.get());
    }

    /**
     * 获取本次HTTP请求的日志
     *
     * @return 日志
     */
    public static RequestLog getLog() {
        return logHolder.get();
    }

    /**
     * 设置当前日志
     *
     * @param log 日志
     */
    public static void setLog(RequestLog log) {
        logHolder.set(log);
    }

    /**
     * 获取处理的类
     *
     * @return 处理的类
     */
    public static Class<?> getHandler() {
        return handlerHolder.get();
    }

    /**
     * 设置处理的类
     */
    public static void setHandler(Class<?> handler) {
        handlerHolder.set(handler);
    }

    public static Optional<Throwable> getCurrentError() {
        return Optional.ofNullable(exceptionHolder.get());
    }

    /**
     * 设置当前异常
     */
    public static void setCurrentError(Throwable e) {
        exceptionHolder.set(e);
        var sysLog = getLog();

        var exception = new RequestLog.Exception();
        exception.setTraceId(sysLog.getTraceId());
        exception.setHappenTime(sysLog.getRequestTime());
        exception.setExceptionClass(e.getClass().getName());
        exception.setExceptionMessage(StringUtils.substring(ExceptionUtils.getMessage(e), 0, 1024));
        exception.setExceptionStack(ExceptionUtils.getStackTrace(e));
        exception.setRootCauseClass(e.getClass().getName());
        exception.setRootCauseMessage(StringUtils.substring(ExceptionUtils.getRootCauseMessage(e), 0, 1024));
        if (e.getStackTrace().length > 0) {
            var firstElement = e.getStackTrace()[0];
            exception.setDeclaringClass(firstElement.getClassName());
            exception.setMethodName(firstElement.getMethodName());
            exception.setFileName(firstElement.getFileName());
            exception.setLineNumber(firstElement.getLineNumber());
        }
        sysLog.setException(exception);
    }

    public static boolean isErrored() {
        return getCurrentError().isPresent();
    }

    /**
     * 清理
     */
    static void clean() {
        logHolder.remove();
        handlerHolder.remove();
        durableHolder.remove();
        exceptionHolder.remove();
    }


}

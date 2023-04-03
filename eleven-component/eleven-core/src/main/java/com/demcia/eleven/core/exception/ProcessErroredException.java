
package com.demcia.eleven.core.exception;

/**
 * 请求处理失败，通常是由于服务器错误，跟客户端关系不大
 *
 */
public class ProcessErroredException extends RuntimeException {

    public ProcessErroredException(Throwable cause) {
        super(cause);
    }

    private ProcessErroredException(String message, Exception cause) {
        super(message, cause);
    }

    /**
     * 构造一个常规的因为外部异常而造成业务处理中断的异常处理
     *
     * @param message 异常描述
     * @param cause   外部异常
     */

    public static ProcessErroredException of(String message, Exception cause) {
        return new ProcessErroredException(message, cause);
    }

    /**
     * 构造一个常规的因为外部异常而造成业务处理中断的异常处理
     *
     * @param message 异常描述
     * @param cause   错误
     * @return 异常实例
     */
    public static ProcessErroredException of(Exception cause, String message) {
        return new ProcessErroredException(message, cause);
    }


    /**
     * 构造一个常规的因为外部异常而造成业务处理中断的异常处理
     *
     * @param cause 外部异常
     */

    public static ProcessErroredException of(Exception cause) {
        return new ProcessErroredException(cause);
    }

    /**
     * 构造一个常规的因为外部异常而造成业务处理中断的异常处理
     *
     * @param cause 外部异常
     */
    public static ProcessErroredException of(String cause) {
        return new ProcessErroredException(cause, null);
    }

}

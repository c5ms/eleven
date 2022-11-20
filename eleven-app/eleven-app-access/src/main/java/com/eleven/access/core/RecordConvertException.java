package com.eleven.access.core;

public class RecordConvertException extends RuntimeException {
    public RecordConvertException() {
    }

    public RecordConvertException(String message) {
        super(message);
    }

    public RecordConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordConvertException(Throwable cause) {
        super(cause);
    }
}

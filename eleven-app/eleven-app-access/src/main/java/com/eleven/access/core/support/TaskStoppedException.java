package com.eleven.access.core.support;

public class TaskStoppedException extends RuntimeException {
    public TaskStoppedException() {
    }

    public TaskStoppedException(String message) {
        super(message);
    }

    public TaskStoppedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskStoppedException(Throwable cause) {
        super(cause);
    }
}

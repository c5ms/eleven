package com.eleven.access.core;

public interface MessageErrorHandler {
    boolean onError(Exception e, Message message, MessageService messageService);
}

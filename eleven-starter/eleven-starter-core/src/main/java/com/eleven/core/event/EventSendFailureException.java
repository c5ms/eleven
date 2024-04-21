package com.eleven.core.event;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class EventSendFailureException extends RuntimeException {

    private final Serializable event;
    private final String topic;

    public EventSendFailureException(Exception cause, Serializable event, String topic) {
        super(cause);
        this.event = event;
        this.topic = topic;
    }

}

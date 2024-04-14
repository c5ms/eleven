package com.eleven.core.event;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class EventSendFailureException extends RuntimeException {

	private final Serializable event;
	private final String topic;

	public EventSendFailureException(Exception cause, Serializable event, String topic) {
		super(cause);
		this.event=event;
		this.topic=topic;
	}

}

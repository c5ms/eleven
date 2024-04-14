package com.eleven.core.event;

import java.io.Serializable;

/**
 * The implementation responses to publish event to the topic, can be 0-1 instance
 */
public interface EventSender {

	void send(Serializable event, String topic) throws Exception;

}

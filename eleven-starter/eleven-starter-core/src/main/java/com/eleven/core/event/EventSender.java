package com.eleven.core.event;

import java.io.Serializable;

/**
 * The implementation responses to publish event to the topic, can be 0-1 instance
 */
public interface EventSender {

    /**
     * send an event to a specific topic
     *
     * @param topic is the topic
     * @param event is the event
     * @throws Exception when send error
     */
    void send(String topic, Serializable event) throws Exception;

}

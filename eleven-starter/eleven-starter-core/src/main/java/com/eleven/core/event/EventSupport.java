package com.eleven.core.event;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventSupport {

    private final ApplicationEventPublisher eventPublisher;
    private final Optional<EventSender> eventSender;

    /**
     * publish event to internal channel, every listener will receive event and process internally via spring event listener
     *
     * @param event the event come from domain layer
     */
    public void publishInternalEvent(Serializable event) {
        eventPublisher.publishEvent(event);
    }

    /**
     * publish event to a particular topic externally, the event will push to an external server, e.g. RabbitMQ,Kafka etc.
     *
     * @param topic is the topic which the event need send to
     * @param event is the event which come from domain layer
     */
    public void publishExternalEvent(String topic, Serializable event) {
        try {
            if (eventSender.isEmpty()) {
                throw new EventSenderNotfoundException();
            }
            eventSender.get().send(topic, event);
        } catch (Exception e) {
            throw new EventSendFailureException(e, event, topic);
        }
    }

}

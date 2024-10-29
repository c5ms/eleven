package com.eleven.hotel.application.listener;

import com.eleven.core.application.event.ApplicationEventIntegrator;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelEventReceiver {
    public static final String QUEUE_HOTEL_EVENT = "hotel_event";
    public static final String QUEUE_HOTEL_EVENT_ERROR = "hotel_event_error";

    private final AmqpTemplate amqpTemplate;
    private final ApplicationEventIntegrator applicationEventDispatcher;

    @RabbitListener(queues = QUEUE_HOTEL_EVENT, ackMode = "MANUAL")
    public void receive(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            applicationEventDispatcher.inbound(message);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("fail to receive event", e);
            amqpTemplate.convertAndSend(QUEUE_HOTEL_EVENT_ERROR, message);
        }
    }

}

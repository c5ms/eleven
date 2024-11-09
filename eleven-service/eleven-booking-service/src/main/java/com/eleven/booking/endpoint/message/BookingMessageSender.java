package com.eleven.booking.endpoint.message;

import com.eleven.core.application.event.ApplicationEventMessage;
import com.eleven.core.application.event.ApplicationEventMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingMessageSender implements ApplicationEventMessageSender {
    public static final String QUEUE_HOTEL_EVENT = "hotel_event";

//    private final AmqpTemplate amqpTemplate;

    @Override
    public void send(ApplicationEventMessage message) {
//        amqpTemplate.convertAndSend(HotelEventMessageSender.QUEUE_HOTEL_EVENT, message);
    }
}

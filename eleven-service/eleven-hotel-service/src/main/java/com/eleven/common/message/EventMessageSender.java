package com.eleven.common.message;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEventMessage;
import com.eleven.core.application.event.ApplicationEventMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageSender implements ApplicationEventMessageSender {
    public static final String QUEUE_HOTEL_EVENT = "hotel.event";

//    private final AmqpTemplate amqpTemplate;

    @Override
    public void send(ApplicationEventMessage message) {
        var body = JSONUtil.toJsonStr(message);
//        amqpTemplate.convertAndSend(EventMessageSender.QUEUE_HOTEL_EVENT, body);
    }

}

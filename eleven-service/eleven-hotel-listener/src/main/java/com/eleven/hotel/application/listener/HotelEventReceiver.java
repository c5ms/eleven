package com.eleven.hotel.application.listener;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.event.ApplicationEventIntegrator;
import com.eleven.core.application.event.ApplicationEventMessage;
import com.eleven.hotel.api.endpoint.model.PlanDto;
import com.eleven.hotel.application.service.PlanHandler;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelEventReceiver {
    public static final String QUEUE_HOTEL_EVENT = "hotel.event";
    public static final String QUEUE_HOTEL_EVENT_ERROR = "hotel.event.error";

    public static final String QUEUE_PLAN_DATA = "hotel.plan.data";
    public static final String QUEUE_PLAN_DATA_ERROR = "hotel.plan.data.error";

    private final PlanHandler planHandler;

    private final AmqpTemplate amqpTemplate;
    private final ApplicationEventIntegrator integrator;

    @RabbitListener(queues = QUEUE_HOTEL_EVENT, ackMode = "MANUAL")
    public void receiveEvent(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            var ems = JSONUtil.toBean(message, ApplicationEventMessage.class);
            integrator.receive(ems);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("fail to receive event", e);
            amqpTemplate.convertAndSend(QUEUE_HOTEL_EVENT_ERROR, message);
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(queues = QUEUE_PLAN_DATA, ackMode = "MANUAL")
    public void receiveData(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            var dto = JSONUtil.toBean(message, PlanDto.class);
            planHandler.sync(dto);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("fail to receive event", e);
            amqpTemplate.convertAndSend(QUEUE_PLAN_DATA_ERROR, message);
            channel.basicNack(tag, false, false);
        }
    }

}

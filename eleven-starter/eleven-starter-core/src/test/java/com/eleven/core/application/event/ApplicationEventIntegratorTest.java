package com.eleven.core.application.event;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.application.event.support.JacksonApplicationEventSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(
        classes = {ApplicationEventIntegrator.class, JacksonApplicationEventSerializer.class, TestEventSender.class, TestEventListener.class}
)
@SpringBootConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationEventIntegratorTest {

    @Autowired
    ApplicationEventIntegrator integrator;

    @Autowired
    TestEventSender eventSender;

    @Autowired
    TestEventListener eventListener;

    @org.junit.jupiter.api.Test
    void send() throws ApplicationEventSerializeException {

        TestEvent event = new TestEvent();
        event.setContent("test event content");
        integrator.send(event);

        event = new TestEvent();
        event.getHeader().setFrom(ApplicationEventOrigin.EXTERNAL);
        event.setContent("test event content");
        integrator.send(event);

        Assertions.assertEquals(1, eventSender.getMessages().size());
    }

    @DisplayName("don't send external message")
    @org.junit.jupiter.api.Test
    void send_external_message() throws ApplicationEventSerializeException {

        TestEvent event = new TestEvent();
        event.setContent("test event content");
        integrator.send(event);

        event = new TestEvent();
        event.getHeader().setFrom(ApplicationEventOrigin.EXTERNAL);
        event.setContent("test event content");
        integrator.send(event);

        Assertions.assertEquals(1, eventSender.getMessages().size());
    }

    @org.junit.jupiter.api.Test
    void receive() throws ApplicationEventSerializeException {
        var message = "{\"cls\":\"com.eleven.core.application.event.TestEvent\",\"event\":\"SystemTestEvent\",\"time\":1730180030238,\"body\":\"{\\\"content\\\":\\\"test event content\\\"}\"}";
        integrator.receive(JSONUtil.toBean(message,ApplicationEventMessage.class));

        message = "{\"cls\":\"com.eleven.core.application.event.NoSuchTestEvent\",\"event\":\"SystemTestEvent\",\"time\":1730180030238,\"body\":\"{\\\"content\\\":\\\"test event content\\\"}\"}";
        integrator.receive(JSONUtil.toBean(message,ApplicationEventMessage.class));

        Assertions.assertEquals(1, eventListener.getEvents().size());
        Assertions.assertEquals(ApplicationEventOrigin.EXTERNAL, eventListener.getEvents().get(0).getHeader().getFrom());
        Assertions.assertEquals(ApplicationHelper.getServiceName(), eventListener.getEvents().get(0).getHeader().getService());
    }


}

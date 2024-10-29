package com.eleven.core.application.event;

import com.eleven.core.application.*;
import com.eleven.core.application.event.support.JacksonApplicationEventSerializer;
import org.junit.jupiter.api.Assertions;
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
    void outbound() throws ApplicationEventSerializeException {

        TestEvent event = new TestEvent();
        event.setContent("test event content");
        integrator.outbound(event);

        event = new TestEvent();
        event.getHeader().setFrom(ApplicationEventOrigin.EXTERNAL);
        event.setContent("test event content");
        integrator.outbound(event);

        Assertions.assertEquals(1, eventSender.getMessages().size());
    }

    @org.junit.jupiter.api.Test
    void inbound() throws ApplicationEventSerializeException {
        var message = "{\"cls\":\"com.eleven.core.application.event.TestEvent\",\"event\":\"SystemTestEvent\",\"time\":1730180030238,\"body\":\"{\\\"content\\\":\\\"test event content\\\"}\"}";
        integrator.inbound(message);

        message = "{\"cls\":\"com.eleven.core.application.event.NoSuchTestEvent\",\"event\":\"SystemTestEvent\",\"time\":1730180030238,\"body\":\"{\\\"content\\\":\\\"test event content\\\"}\"}";
        integrator.inbound(message);

        Assertions.assertEquals(1, eventListener.getEvents().size());
        Assertions.assertEquals(ApplicationEventOrigin.EXTERNAL, eventListener.getEvents().get(0).getHeader().getFrom());
        Assertions.assertEquals(ApplicationHelper.getServiceName(), eventListener.getEvents().get(0).getHeader().getService());
    }


}

package com.eleven.core.application.event;

public interface ApplicationEventMessageSender {

    void send(ApplicationEventMessage message);

}

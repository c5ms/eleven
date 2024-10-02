package com.eleven.upms.core.event;


import com.eleven.core.event.ElevenEvent;

public record AccessTokenCreatedEvent(String token) implements ElevenEvent {

}

package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record AccessTokenCreatedEvent(String token) implements ElevenEvent {

}

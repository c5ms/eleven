package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record UserCreatedEvent(String userId) implements ElevenEvent {
}

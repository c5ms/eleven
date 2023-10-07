package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record UserCreatedEvent(String userId) implements ElevenEvent {
}

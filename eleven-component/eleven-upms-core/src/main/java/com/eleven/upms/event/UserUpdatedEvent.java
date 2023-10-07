package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record UserUpdatedEvent(String userId) implements ElevenEvent {
}

package com.eleven.upms.core.event;

import com.eleven.core.event.ElevenEvent;

public record UserUpdatedEvent(String userId) implements ElevenEvent {
}

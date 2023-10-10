package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record UserUpdatedEvent(String userId) implements ElevenEvent {
}

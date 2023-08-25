package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record UserLockedEvent(String userId) implements ElevenEvent {
}

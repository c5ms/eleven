package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record UserLockedEvent(String userId) implements ElevenEvent {
}

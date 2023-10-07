package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record UserUnLockedEvent(String userId) implements ElevenEvent {
}

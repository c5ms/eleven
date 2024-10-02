package com.eleven.upms.core.event;

import com.eleven.core.event.ElevenEvent;

public record UserUnLockedEvent(String userId) implements ElevenEvent {
}

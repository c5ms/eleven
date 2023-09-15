package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record UserUnLockedEvent(String userId) implements ElevenEvent {
}

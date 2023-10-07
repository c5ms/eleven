package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record UserDeletedEvent(String userId) implements ElevenEvent {

}

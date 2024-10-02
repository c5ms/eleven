package com.eleven.upms.core.event;

import com.eleven.core.event.ElevenEvent;

public record UserDeletedEvent(String userId) implements ElevenEvent {

}

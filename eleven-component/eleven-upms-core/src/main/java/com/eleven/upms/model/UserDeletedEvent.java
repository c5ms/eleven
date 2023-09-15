package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record UserDeletedEvent(String userId) implements ElevenEvent {

}

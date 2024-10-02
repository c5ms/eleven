package com.eleven.upms.core.event;

import com.eleven.core.event.ElevenEvent;

public record UserLoginEvent(String userId) implements ElevenEvent {

}

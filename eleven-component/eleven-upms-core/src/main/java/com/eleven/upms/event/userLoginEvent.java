package com.eleven.upms.event;

import com.eleven.core.event.ElevenEvent;

public record userLoginEvent(String userId) implements ElevenEvent {

}

package com.eleven.upms.model;

import com.eleven.core.event.ElevenEvent;

public record userLoginEvent(String userId) implements ElevenEvent {

}

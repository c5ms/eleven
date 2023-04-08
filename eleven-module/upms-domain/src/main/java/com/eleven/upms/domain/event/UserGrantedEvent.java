package com.eleven.upms.domain.event;

import com.eleven.upms.domain.Authority;

public record UserGrantedEvent(String userId, Authority authority) {
}

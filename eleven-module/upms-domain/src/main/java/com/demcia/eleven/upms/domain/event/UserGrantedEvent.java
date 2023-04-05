package com.demcia.eleven.upms.domain.event;

import com.demcia.eleven.upms.domain.Authority;

public record UserGrantedEvent(String userId, Authority authority) {
}

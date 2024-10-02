package com.eleven.gateway.admin.domain.event;

import lombok.Value;

@Value
public class GateResourceUpdateEvent {
    String resourceId;
}

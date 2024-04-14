package com.eleven.gateway.admin.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayState {
    private boolean running;
}

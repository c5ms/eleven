package com.eleven.gateway.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayConfig {

    Server server;
    HttpClient httpClient;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HttpClient {
        int maxConnections;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Server {
        int port;
        String name;
        Boolean running;
    }
}

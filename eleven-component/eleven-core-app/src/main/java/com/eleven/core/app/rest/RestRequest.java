package com.eleven.core.app.rest;

import lombok.Builder;
import lombok.Value;

/**
 * 封装了客户端信息，表示本次请求信息
 */
@Value
@Builder
public class RestRequest {
    String ip;
}

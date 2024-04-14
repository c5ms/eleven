package com.eleven.gateway.core;

import lombok.Builder;
import lombok.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;

import java.util.Optional;

@Value
public class GatewayContent {

    MediaType mediaType;
    Resource body;
    String encoding;
    CacheControl cacheControl;


    @Builder
    public GatewayContent(Resource body,
                          MediaType mediaType,
                          String encoding,
                          CacheControl cacheControl) {
        this.mediaType = Optional.ofNullable(mediaType).orElse(MediaType.APPLICATION_OCTET_STREAM);
        this.body = body;
        this.encoding = encoding;
        this.cacheControl = cacheControl;
    }


}

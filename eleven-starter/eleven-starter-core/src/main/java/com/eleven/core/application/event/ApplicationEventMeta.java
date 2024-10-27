package com.eleven.core.application.event;

import lombok.Data;

@Data
public class ApplicationEventMeta {

    private transient From from = From.INTERNAL;

    enum From {
        INTERNAL,
        EXTERNAL;
    }

}

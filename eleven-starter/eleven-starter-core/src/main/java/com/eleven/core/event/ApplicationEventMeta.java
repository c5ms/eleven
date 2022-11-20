package com.eleven.core.event;

import lombok.Data;

@Data
public class ApplicationEventMeta {

    private transient From from = From.INTERNAL;

    enum From {
        INTERNAL,
        EXTERNAL;
    }

}

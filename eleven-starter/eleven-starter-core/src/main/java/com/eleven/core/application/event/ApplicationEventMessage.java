package com.eleven.core.application.event;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplicationEventMessage implements Serializable {

    private String className;

    private String service;

    private String event;

    private LocalDateTime time;

    private String body;
}

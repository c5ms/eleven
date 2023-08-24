package com.eleven.core.message;

import lombok.Value;

import java.io.Serializable;

@Value
public class Message implements Serializable {
    String topic;
    String body;
}

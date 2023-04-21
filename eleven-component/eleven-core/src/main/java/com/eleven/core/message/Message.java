package com.eleven.core.message;

import lombok.Value;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Value
public class Message implements Serializable {
    String topic;
    Map<String, String> header = new HashMap<>();
    String body;
}

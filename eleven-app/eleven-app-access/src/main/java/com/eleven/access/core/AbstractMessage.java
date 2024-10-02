package com.eleven.access.core;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzc
 */
@Getter
@Setter
public abstract class AbstractMessage implements Message {
    private static final long serialVersionUID = 1L;
    private final Map<String, String> header = new HashMap<>();
    private String topic;
    private Throwable error;
}

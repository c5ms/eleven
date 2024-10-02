package com.eleven.access.admin.domain.dto;

import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.access.core.HealthInformation;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * A DTO for the {@link MessageListenerDefinition} entity
 */
@Data
public class ListenerDto implements Serializable {
    private String id;
    private String label;
    private String description;
    private String headers;
    private String topic;
    private String resourceComponent;
    private String resourceId;
    private Integer threads = 1;
    private Map<String, String> config;
    private boolean running;
    private HealthInformation healthy;
}

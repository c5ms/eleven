package com.eleven.access.admin.domain.dto;

import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import com.cnetong.access.core.HealthInformation;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * A DTO for the {@link MessageProducerDefinition} entity
 */
@Data
public class ProducerDto implements Serializable {
    private String id;
    private String label;
    private String description;
    private String topic;
    private String resourceComponent;
    private String resourceId;
    private Map<String, String> config;
    private boolean running;
    private HealthInformation healthy;
}

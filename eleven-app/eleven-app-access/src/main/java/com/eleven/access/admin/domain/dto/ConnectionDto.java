package com.eleven.access.admin.domain.dto;

import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.access.core.HealthInformation;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * A DTO for the {@link ResourceDefinition} entity
 */
@Data
public class ConnectionDto implements Serializable {
    private String id;
    private String label;
    private String component;
    private String description;
    private Map<String, String> config;
    private HealthInformation healthy;
}

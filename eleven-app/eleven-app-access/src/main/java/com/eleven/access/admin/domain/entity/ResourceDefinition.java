package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.persist.jpa.convert.StringStringMapConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "base_resource")
public class ResourceDefinition extends AbstractDomain {

    @Column(name = "label_")
    private String label;

    @Column(name = "component_")
    private String component;

    @Schema(description = "描述信息")
    @Column(name = "description_", length = 500)
    private String description;

    @Lob
    @Column(name = "config_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> config = new HashMap<>();

    public void update(ResourceDefinition resourceDefinition) {
        setConfig(resourceDefinition.getConfig());
        setDescription(resourceDefinition.getDescription());
        setLabel(resourceDefinition.getLabel());
        setComponent(resourceDefinition.getComponent());
    }
}

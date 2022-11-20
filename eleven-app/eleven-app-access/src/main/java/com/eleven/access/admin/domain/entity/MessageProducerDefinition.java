package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.persist.jpa.convert.StringStringMapConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "producers")
@Table(name = "message_producer")
public class MessageProducerDefinition extends AbstractDomain {

    @Schema(description = "名称")
    @Column(name = "label_", length = 100)
    private String label;

    @Schema(description = "描述信息")
    @Column(name = "description_", length = 500)
    private String description;

    @Size(min = 2, max = 100, message = "长度在2到100之间")
    @Pattern(regexp = "^\\w+$", message = "只能是数字、字母、下划线")
    @Schema(description = "数据主题")
    @Column(name = "topic_", length = 100)
    private String topic;

    @Schema(description = "连接类型")
    @Column(name = "resource_component_")
    private String resourceComponent;

    @Schema(description = "连接 ID")
    @Column(name = "resource_id_")
    private String resourceId;

    @Lob
    @Column(name = "config_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> config = new HashMap<>();

    @Schema(description = "运行状态")
    @Column(name = "running_", length = 100)
    private Boolean running = false;


    public void update(MessageProducerDefinition update) {
        setRunning(update.getRunning());
        setConfig(update.getConfig());
        setLabel(update.getLabel());
        setDescription(update.getDescription());
        setTopic(update.getTopic());
        setResourceComponent(update.getResourceComponent());
        setResourceId(update.getResourceId());
        setConfig(update.getConfig());
    }
}

package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.persist.jpa.convert.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "路由规则")
@Table(name = "message_rule")
public class MessageRuleDefinition extends AbstractDomain {

    @Schema(description = "来源名称")
    @Column(name = "label_", length = 100)
    private String label;

    @Schema(description = "排序")
    @Column(name = "order_")
    private Integer order;

    @Schema(description = "主题")
    @Column(name = "topic_")
    private String topic;

    @Schema(description = "输出 ID")
    @Column(name = "producer_id_")
    private String producerId;

    @Schema(description = "匹配规则")
    @Convert(converter = StringListConverter.class)
    @Column(name = "filters_", length = 2000)
    private List<String> filters;

    @Schema(description = "处理器")
    @Convert(converter = StringListConverter.class)
    @Column(name = "processors_", length = 2000)
    private List<String> processors;

    @Schema(description = "错误")
    @Column(name = "error_", length = 2000)
    private String error;

    @Schema(description = "发布状态")
    @Column(name = "published_", nullable = false)
    private boolean published = false;

    @ManyToOne()
    @JoinColumn(name = "producer_id_", updatable = false, insertable = false)
    private MessageProducerDefinition producer;

}

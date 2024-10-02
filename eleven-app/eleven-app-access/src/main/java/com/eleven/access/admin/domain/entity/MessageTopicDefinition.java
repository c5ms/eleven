package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "消息主题")
@Table(name = "message_topic")
public class MessageTopicDefinition extends AbstractDomain {

    @NotBlank(message = "主题名不能空")
    @Schema(description = "主题名称")
    @Column(name = "name_", length = 200)
    private String name;

    @Schema(description = "分区策略")
    @Column(name = "partition_mode_")
    private PartitionStrategy partitionStrategy = PartitionStrategy.none;

    @Schema(description = "当前分区")
    @Column(name = "current_partition_")
    private String currentPartition;

    @Schema(description = "运行状态")
    @Column(name = "running_", length = 100)
    private Boolean running = false;

    @JsonIgnore
    @OrderBy("order asc")
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "topic_", referencedColumnName = "name_")
    private List<MessageRuleDefinition> rules = new ArrayList<>();

    public void update(MessageTopicDefinition definition) {
        this.setName(definition.getName());
        this.setCurrentPartition(definition.getCurrentPartition());
        this.setPartitionStrategy(definition.getPartitionStrategy());
        this.setRunning(definition.getRunning());
    }

    public enum PartitionStrategy {
        /**
         * 固定分区
         */
        fixed,
        /**
         * 按天
         */
        day,
        /**
         * 按月
         */
        month,
        /**
         * 按年
         */
        year,
        /**
         * 不分区
         */
        none,
    }
}

package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "消息分区")
@Table(name = "message_partition")
public class MessagePartitionDefinition extends AbstractDomain {

    @NotBlank(message = "分区名称不能为空")
    @Size(min = 1, max = 50, message = "分区名称长度在1到50之间")
    @Pattern(regexp = "^\\w+$", message = "分区名称只能是数字、字母、下划线")
    @Schema(description = "分区名称")
    @Column(name = "name")
    private String name;

    @Schema(description = "当前分区")
    @Column(name = "current_")
    private Boolean current = false;

    public void update(MessagePartitionDefinition definition) {
        this.current = definition.getCurrent();
    }
}

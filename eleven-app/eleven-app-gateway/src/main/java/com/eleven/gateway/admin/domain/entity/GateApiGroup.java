package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.eleven.gateway.admin.domain.action.GateApiGroupSaveAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "API组")
@Table(name = "gate_api_group")
public class GateApiGroup extends AbstractDomain {

    @Schema(description = "名称")
    @Column(name = "name_", length = 100)
    private String name;

    @Schema(description = "描述")
    @Column(name = "description_", length = 200)
    private String description;

    @Schema(description = "访问次数")
    @Column(name = "stat_request_count_", updatable = false)
    private Integer statRequestCount = 0;

    public GateApiGroup() {
    }

    public GateApiGroup(GateApiGroupSaveAction action) {
        BeanUtils.copyProperties(action, this);
    }

    public void modify(GateApiGroupSaveAction saveAction) {
        BeanUtils.copyProperties(saveAction, this);
    }

}

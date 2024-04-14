package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.eleven.gateway.admin.domain.action.GateAppSaveAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yz
 */
@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "网关应用")
@Table(name = "gate_app")
public class GateApp extends AbstractDomain {

    public final static String GATE_APP_TYPE = "application";

    @Basic
    @Schema(description = "应用标识")
    @Column(name = "app_id_", length = 100, nullable = false)
    private String appId;

    @Schema(description = "应用名称")
    @Column(name = "name_", length = 100)
    private String name;

    @Schema(description = "描述")
    @Column(name = "description_", length = 200)
    private String description;

    @Schema(description = "禁用")
    @Column(name = "forbidden_", length = 1)
    private Boolean forbidden = false;

    @Column(name = "api_id_")
    @ElementCollection
    @CollectionTable(name = "gate_app_api", joinColumns = @JoinColumn(name = "app_id_"))
    private Set<String> apis = new HashSet<>();

    @Schema(description = "访问令牌")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "app_id_", referencedColumnName = "app_id_", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), updatable = false, insertable = false)
    private GateAppToken token = null;


    @Schema(description = "访问次数")
    @Column(name = "stat_request_count_",updatable = false)
    private Integer statRequestCount=0;

    public GateApp() {
    }

    public GateApp(GateAppSaveAction action) {
        BeanUtils.copyProperties(action, this);
    }

    public void forbid() {
        this.forbidden = !this.forbidden;
    }

    public void modify(GateAppSaveAction saveAction) {
        BeanUtils.copyProperties(saveAction, this);
    }

}

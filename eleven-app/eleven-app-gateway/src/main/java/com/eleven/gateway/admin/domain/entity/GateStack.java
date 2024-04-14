package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.persist.jpa.convert.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "gate_stack")
public class GateStack extends AbstractPublishableDomain {

    @Size(max = 200, message = "站点名最多 200 个字符")
    @NotBlank(message = "站点名不可为空")
    @Schema(description = "站点名")
    @Column(name = "name_", length = 200)
    private String name;

    @Schema(description = "排序")
    @Column(name = "order_")
    private Integer order;

    @Schema(description = "域名")
    @Column(name = "host_")
    private String host;

    @Schema(description = "过滤器")
    @Convert(converter = StringListConverter.class)
    @Column(name = "filters_", length = 2000)
    private List<String> filters;

    @JsonIgnore
    @OrderBy("order asc")
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "stack_id_")
    private List<GateRoute> gateRoutes = new ArrayList<>();

    public void update(GateStack gateStack) {
        this.setName(gateStack.getName());
        this.setFilters(gateStack.getFilters());
        this.setOrder(gateStack.getOrder());
        this.setHost(gateStack.getHost());
    }

}

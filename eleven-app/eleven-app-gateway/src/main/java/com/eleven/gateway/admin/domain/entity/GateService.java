package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.persist.jpa.convert.StringListConverter;
import com.cnetong.common.persist.jpa.convert.StringSetConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "服务")
@Table(name = "gate_service")
public class GateService extends AbstractDomain {

    @Size(max = 100, message = "服务名最多 100 个字符")
    @NotBlank(message = "服务名不可为空")
    @Schema(description = "服务名")
    @Column(name = "name_", length = 100)
    private String name;

    @Size(max = 200, message = "服务描述最多 200 个字符")
    @Schema(description = "服务描述")
    @Column(name = "description_", length = 200)
    private String description;

    @Schema(description = "服务实例")
    @Convert(converter = StringSetConverter.class)
    @Column(name = "instances_", length = 2000)
    private Set<String> instances = new HashSet<>();

    @Schema(description = "请求过滤器")
    @Convert(converter = StringListConverter.class)
    @Column(name = "filters_", length = 2000)
    private List<String> filters;

    public void update(GateService definition) {
        this.setName(definition.getName());
        this.setDescription(definition.getDescription());
        this.setFilters(definition.getFilters());
        this.setInstances(definition.getInstances().stream().map(StringUtils::trim).filter(StringUtils::isNotBlank).collect(Collectors.toSet()));
    }

}

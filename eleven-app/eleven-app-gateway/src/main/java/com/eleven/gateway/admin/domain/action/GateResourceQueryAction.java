package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.eleven.gateway.admin.domain.entity.GateResource;
import com.eleven.gateway.admin.domain.entity.GateService;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class GateResourceQueryAction extends JpaPageableQueryAction<GateResource> {

    @Schema(description = "资源名")
    private String name;

    @Override
    public Specification<GateResource> toSpecification() {
        return Specifications.<GateResource>and()
                .like(StringUtils.isNotBlank(name), GateService.Fields.name, "%" + StringUtils.trim(name) + "%")
                .build();
    }
}

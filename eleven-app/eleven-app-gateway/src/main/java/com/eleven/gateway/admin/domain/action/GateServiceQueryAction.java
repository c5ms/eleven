package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.eleven.gateway.admin.domain.entity.GateService;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class GateServiceQueryAction extends JpaPageableQueryAction<GateService> {

    @Schema(description = "服务名")
    private String name;


    @Override
    public Specification<GateService> toSpecification() {
        return Specifications.<GateService>and()
                .like(StringUtils.isNotBlank(name), GateService.Fields.name, "%" + StringUtils.trim(name) + "%")
                .build();
    }
}

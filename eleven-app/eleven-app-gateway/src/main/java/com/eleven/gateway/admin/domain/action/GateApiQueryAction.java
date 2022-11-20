package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.AbstractIdDomain;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.eleven.gateway.admin.domain.entity.GateApi;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class GateApiQueryAction extends JpaPageableQueryAction<GateApi> {

    @Schema(description = "路由名")
    private String name;

    @Schema(description = "路由ID")
    private String id;

    @Schema(description = "路由分组")
    private String group;

    @Override
    public Specification<GateApi> toSpecification() {
        return Specifications.<GateApi>and()
                .eq(StringUtils.isNotBlank(id), AbstractIdDomain.Fields.id, id)
                .eq(StringUtils.isNotBlank(group), GateApi.Fields.group, group)
                .like(StringUtils.isNotBlank(name), GateApi.Fields.name, "%" + StringUtils.trim(name) + "%")
                .build();
    }
}

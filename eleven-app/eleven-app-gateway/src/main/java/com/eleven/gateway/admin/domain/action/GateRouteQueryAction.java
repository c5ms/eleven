package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.AbstractIdDomain;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.eleven.gateway.admin.domain.entity.GateRoute;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class GateRouteQueryAction extends JpaPageableQueryAction<GateRoute> {

    @Schema(description = "路由名")
    private String name;

    @Schema(description = "路由ID")
    private String id;

    @Schema(description = "站点")
    private String hostId;

    @Schema(description = "全局路由")
    private Boolean global;

    @Schema(description = "站点")
    private String stackId;

    @Override
    public Specification<GateRoute> toSpecification() {
        return Specifications.<GateRoute>and()
                .eq(StringUtils.isNotBlank(id), AbstractIdDomain.Fields.id, id)
                .eq(StringUtils.isNotBlank(stackId), GateRoute.Fields.stackId, stackId)
                .like(StringUtils.isNotBlank(name), GateRoute.Fields.name, "%" + StringUtils.trim(name) + "%")
                .eq(StringUtils.isNotBlank(hostId), GateRoute.Fields.stackId, hostId)
                .predicate(BooleanUtils.isTrue(global), (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(GateRoute.Fields.stackId)))
                .build();
    }
}

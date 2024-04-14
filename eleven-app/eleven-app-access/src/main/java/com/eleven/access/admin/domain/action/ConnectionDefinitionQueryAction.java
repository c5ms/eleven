package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class ConnectionDefinitionQueryAction extends JpaPageableQueryAction<ResourceDefinition> {
    private String component;

    @Override
    public Specification<ResourceDefinition> toSpecification() {
        return Specifications.<ResourceDefinition>and()
                .eq(StringUtils.isNotBlank(component), ResourceDefinition.Fields.component, component)
                .build();
    }
}

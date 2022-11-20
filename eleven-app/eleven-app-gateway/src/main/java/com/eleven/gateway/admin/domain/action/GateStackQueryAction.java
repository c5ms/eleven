package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.action.JpaQueryAction;
import com.eleven.gateway.admin.domain.entity.GateStack;
import com.github.wenhao.jpa.Specifications;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class GateStackQueryAction extends JpaQueryAction<GateStack> {

    @Override
    public Specification<GateStack> toSpecification() {
        return Specifications.<GateStack>and()
                .build();
    }
}

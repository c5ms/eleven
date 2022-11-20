package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.MessageRuleDefinition;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class MessageRuleQueryAction extends JpaPageableQueryAction<MessageRuleDefinition> {

    private String topic;

    @Override
    public Specification<MessageRuleDefinition> toSpecification() {
        return Specifications.<MessageRuleDefinition>and()
                .eq(StringUtils.isNotBlank(topic), MessageRuleDefinition.Fields.topic, topic)
                .build();
    }
}

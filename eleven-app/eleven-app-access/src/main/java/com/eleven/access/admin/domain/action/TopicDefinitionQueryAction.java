package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.MessageTopicDefinition;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class TopicDefinitionQueryAction extends JpaPageableQueryAction<MessageTopicDefinition> {

    @Override
    public Specification<MessageTopicDefinition> toSpecification() {
        return Specifications.<MessageTopicDefinition>and()
                .build();
    }
}

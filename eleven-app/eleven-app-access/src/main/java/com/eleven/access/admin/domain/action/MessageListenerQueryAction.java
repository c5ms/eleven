package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.access.core.Message;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class MessageListenerQueryAction extends JpaPageableQueryAction<MessageListenerDefinition> {

    private Message.Direction direction;

    @Override
    public Specification<MessageListenerDefinition> toSpecification() {
        return Specifications.<MessageListenerDefinition>and()
                .build();
    }
}

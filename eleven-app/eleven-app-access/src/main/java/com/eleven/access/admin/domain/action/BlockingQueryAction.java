package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@Getter
@Setter
public class BlockingQueryAction extends JpaPageableQueryAction<MessageBlocking> {

    @Parameter(description = "消息主题")
    private String topic;

    @Parameter(description = "已解决")
    private Boolean solve;

    @Override
    public Specification<MessageBlocking> toSpecification() {
        return Specifications.<MessageBlocking>and()
                .eq(StringUtils.isNotBlank(topic), MessageBlocking.Fields.topic, topic)
                .eq(Objects.nonNull(solve), MessageBlocking.Fields.solve, solve)
                .build();
    }
}

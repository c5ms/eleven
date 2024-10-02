package com.eleven.gateway.admin.domain.action;

import com.cnetong.common.domain.AbstractDomain;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.eleven.gateway.admin.domain.entity.GateApp;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yz
 */
@Getter
@Setter
public class GateAppQueryAction extends JpaPageableQueryAction<GateApp> {

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "应用标识")
    private String appId;

    @Schema(description = "可用")
    private Boolean forbidden;

    @Schema(description = "应用标识")
    private LocalDateTime timeStart;

    @Schema(description = "应用标识")
    private LocalDateTime timeEnd;

    @Override
    public Specification<GateApp> toSpecification() {
        return Specifications.<GateApp>and()
                .like(StringUtils.isNotBlank(name), GateApp.Fields.name, "%" + StringUtils.trim(name) + "%")
                .eq(StringUtils.isNotBlank(appId), GateApp.Fields.appId, appId)
                .eq(BooleanUtils.isTrue(forbidden), GateApp.Fields.forbidden, forbidden)
                .ge(Objects.nonNull(timeStart), AbstractDomain.Fields.createDate, timeStart)
                .le(Objects.nonNull(timeEnd), AbstractDomain.Fields.createDate, timeEnd)
                .build();
    }

}

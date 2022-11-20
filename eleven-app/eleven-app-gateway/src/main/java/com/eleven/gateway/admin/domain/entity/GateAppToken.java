package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yz
 */
@Deprecated
@Getter
@Setter
@Entity
@FieldNameConstants
@Schema(description = "网关应用令牌")
@Table(name = "gate_app_token")
public class GateAppToken extends AbstractIdDomain {

    @Schema(description = "应用唯一标识")
    @Column(name = "app_id_", length = 100)
    private String appId;

    @Schema(description = "标识")
    @Column(name = "token_", length = 500)
    private String token;

    @Schema(description = "过期时间")
    @Column(name = "expire_at_")
    private LocalDateTime expireAt;

}

package com.eleven.gateway.admin.domain.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yz
 */
@Getter
@Setter
public class GateAppSaveAction implements Serializable {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "应用唯一标识")
    private String appId;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "禁用")
    private Boolean forbidden = false;

}

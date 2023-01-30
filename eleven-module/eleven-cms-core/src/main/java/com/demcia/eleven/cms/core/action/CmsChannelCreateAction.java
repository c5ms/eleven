package com.demcia.eleven.cms.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 栏目创建指令
 */
@Getter
@Setter
@Accessors(chain = true)
public class CmsChannelCreateAction implements Serializable {

    @Schema(description = "栏目标题")
    @NotBlank(message = "栏目标题不能为空")
    private String title;

    @Schema(description = "父级栏目")
    private String parentId;

    @Schema(description = "栏目描述")
    private String description;

}

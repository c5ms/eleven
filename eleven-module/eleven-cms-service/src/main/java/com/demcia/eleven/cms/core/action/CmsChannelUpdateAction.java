package com.demcia.eleven.cms.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 栏目创建指令
 */
@Data
@Accessors(chain = true)
public class CmsChannelUpdateAction implements Serializable {

    @Schema(description = "栏目标题")
    @NotBlank(message = "栏目标题不能为空")
    private String title;

    private String description;

}

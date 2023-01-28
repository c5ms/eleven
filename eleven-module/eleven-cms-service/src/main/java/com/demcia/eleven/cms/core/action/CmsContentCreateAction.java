package com.demcia.eleven.cms.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CmsContentCreateAction implements Serializable {

    private String title;

    private String body;

    @NotBlank(message = "栏目不能为空")
    private String channel;

    private Integer sortIndex;

    private boolean titleIsBold;

    private String titleColor;

    private String shortTitle;

    private int status;

    private boolean top;

}

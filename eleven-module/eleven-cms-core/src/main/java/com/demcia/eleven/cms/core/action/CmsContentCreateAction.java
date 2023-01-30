package com.demcia.eleven.cms.core.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CmsContentCreateAction implements Serializable {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "标题加粗")
    private boolean titleIsBold = false;

    @Schema(description = "标题颜色")
    private String titleColor = "#ffffff";

    @Schema(description = "副标题")
    private String shortTitle;

    @Schema(description = "正文")
    private String body;

    @NotBlank(message = "栏目不能为空")
    private String channel;

//    @Schema(description = "排序")
//    private Integer sortIndex;

    @Schema(description = "外部链接")
    private String outLink;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "摘要")
    private String summary;


}

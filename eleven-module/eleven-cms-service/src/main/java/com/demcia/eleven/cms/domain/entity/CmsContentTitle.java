package com.demcia.eleven.cms.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@FieldNameConstants
public class CmsContentTitle implements Serializable {

    /**
     * 内容标题
     */
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    /**
     * 内容标题是否加粗
     */
    @Column(name = "title_is_bold_")
    private boolean titleIsBold = false;

    /**
     * 内容标题的颜色
     */
    @Column(name = "title_color_", length = 50)
    private String titleColor;

    /**
     * 简短标题
     */
    @Column(name = "short_title_", length = 150)
    private String shortTitle;

}

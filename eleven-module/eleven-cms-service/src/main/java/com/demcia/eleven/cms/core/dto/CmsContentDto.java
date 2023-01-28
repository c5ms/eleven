package com.demcia.eleven.cms.core.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CmsContentDto implements Serializable {
    private String id;
    private Integer sortIndex;
    private CmsChannelDto channel;
    private String title;
    private boolean titleIsBold;
    private String titleColor;
    private String shortTitle;
    private int status;
    private int views;
    private boolean top;
    private boolean recycle;
    private String body;
}
package com.demcia.eleven.cms.core.dto;

import com.demcia.eleven.cms.core.enums.CmsContentState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CmsContentDto implements Serializable {

    private String id;
    private Integer sortIndex;
    private CmsChannelDto channel;
    private String channelId;
    private String summary;
    private int views;
    private boolean top;
    private boolean recycle;
    private String author;
    private String source;
    private String keywords;
    private String outLink;
    private LocalDateTime publishTime;
    private CmsContentState state;
    private CmsContentTitleDto title;
    private CmsContentBodyDto body;
    private CmsContentExtDto ext;

    @Data
    public static class CmsContentExtDto implements Serializable {

    }

    @Data
    public static class CmsContentBodyDto implements Serializable {
        private String text;
    }

    @Data
    public static class CmsContentTitleDto implements Serializable {
        private String title;
        private boolean titleIsBold;
        private String titleColor;
        private String shortTitle;
    }
}
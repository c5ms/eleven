package com.demcia.eleven.cms.core.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CmsContentSummaryDto implements Serializable {
    private String id;
    private String title;
    private CmsChannelDto channel;
    private String body;
}
package com.demcia.eleven.cms.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CmsChannelDto implements Serializable {
    private String id;
    private String parentId;
    private String title;
    private String description;
}
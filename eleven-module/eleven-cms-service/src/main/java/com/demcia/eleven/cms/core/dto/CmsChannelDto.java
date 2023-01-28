package com.demcia.eleven.cms.core.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CmsChannelDto implements Serializable {
    private final String id;
    private String title;
    private String description;
}
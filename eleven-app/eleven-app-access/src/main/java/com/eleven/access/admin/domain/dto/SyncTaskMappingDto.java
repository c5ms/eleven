package com.eleven.access.admin.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SyncTaskMappingDto implements Serializable {
    private String collection;
    private String transformer;
    private String sourceName;
    private String targetName;
    private Boolean isUnique;
}

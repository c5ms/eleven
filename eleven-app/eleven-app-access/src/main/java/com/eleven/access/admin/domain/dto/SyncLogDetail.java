package com.eleven.access.admin.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SyncLogDetail implements Serializable {

    private String taskTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private Long totalCount;
    private String exceptionMessage;
    private String exceptionStack;
    private Boolean success;
}

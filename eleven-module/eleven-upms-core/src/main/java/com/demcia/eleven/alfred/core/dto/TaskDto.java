package com.demcia.eleven.alfred.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class TaskDto implements Serializable {
    private String id;
    private String subject;
    private String description;
    private String directorId;
    private String assistantId;
    private LocalDate deadline;
    private String state;
    private LocalDateTime lastProcessTime;
}
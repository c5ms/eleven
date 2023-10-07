package com.eleven.doney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private String id;
    private String title;
    private String description;
    private String state;
    private String url;
    private String code;

    private Integer newIssueAlarm;
}

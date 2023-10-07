package com.eleven.doney.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

@Schema(description = "项目摘要", name = "ProjectSummary")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSummary {

    private String id ;
    private String title ;

}

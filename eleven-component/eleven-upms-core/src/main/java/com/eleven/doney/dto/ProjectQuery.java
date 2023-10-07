package com.eleven.doney.dto;

import com.eleven.core.domain.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectQuery extends Pagination {

    @Schema(description = "项目标题")
    String title;

}

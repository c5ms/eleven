package com.eleven.core.model;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页查询通用模型
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Pagination implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    @Parameter(description = "页码")
    private int page = 0;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Parameter(description = "页长")
    private int size = 20;


    public Pagination(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public static Pagination of(int page, int size) {
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 20;
        }
        return new Pagination(page, size);
    }

}

package com.demcia.eleven.core.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页查询通用模型
 */
@Data
@Accessors(chain = true)
public class Pagination implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    private Integer page;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    private Integer size;

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

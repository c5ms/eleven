package com.eleven.framework.web.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Pagination {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    @Schema(description = "页码", defaultValue = "1",example = "1")
    private int page = 0;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数至少为1条")
    @Max(value = 1000, message = "每页条数不能超过1000")
    @Schema(description = "页长", defaultValue = "20",example = "20")
    private int size = 20;

    public Pageable toPageable() {
        if (page == 0) {
            page = 1;
        }
        return PageRequest.of(getPage() - 1, getSize());
    }

}

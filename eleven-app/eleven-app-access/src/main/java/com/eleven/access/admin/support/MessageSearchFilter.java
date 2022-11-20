package com.eleven.access.admin.support;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSearchFilter extends MessageQueryFilter {

    @Parameter(description = "搜索关键字，仅ES引擎支持")
    private String q;

}

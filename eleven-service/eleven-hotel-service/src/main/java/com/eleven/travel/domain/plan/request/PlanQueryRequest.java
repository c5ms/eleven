package com.eleven.travel.domain.plan.request;

import com.eleven.framework.web.model.Pagination;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PlanQueryRequest extends Pagination {
    private String planName;
}

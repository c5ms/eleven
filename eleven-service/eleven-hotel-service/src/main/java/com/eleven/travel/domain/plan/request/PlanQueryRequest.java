package com.eleven.travel.domain.plan.request;

import com.eleven.framework.web.model.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PlanQueryRequest extends PageRequest {
    private String planName;
}

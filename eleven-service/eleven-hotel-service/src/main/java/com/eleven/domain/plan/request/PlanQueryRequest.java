package com.eleven.domain.plan.request;

import com.eleven.core.interfaces.model.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PlanQueryRequest extends PageRequest {
    private String planName;
}

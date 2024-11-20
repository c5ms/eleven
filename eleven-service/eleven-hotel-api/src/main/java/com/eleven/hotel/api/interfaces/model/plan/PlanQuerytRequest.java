package com.eleven.hotel.api.interfaces.model.plan;

import com.eleven.core.interfaces.model.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanQuerytRequest extends PageRequest {
    private String planName;
}

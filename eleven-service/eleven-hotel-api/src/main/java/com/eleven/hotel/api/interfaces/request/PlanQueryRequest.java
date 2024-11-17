package com.eleven.hotel.api.interfaces.request;

import com.eleven.core.application.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanQueryRequest extends PageQuery {
    private String planName;
}

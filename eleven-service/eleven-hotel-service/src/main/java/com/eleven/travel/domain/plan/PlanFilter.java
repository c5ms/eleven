package com.eleven.travel.domain.plan;

import com.eleven.framework.web.model.PageRequest;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PlanFilter extends PageRequest {
    private Long hotelId;
    private String planName;
}

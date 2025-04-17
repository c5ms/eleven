package com.eleven.travel.domain.plan;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PlanFilter {
    private Long hotelId;
    private String planName;
}

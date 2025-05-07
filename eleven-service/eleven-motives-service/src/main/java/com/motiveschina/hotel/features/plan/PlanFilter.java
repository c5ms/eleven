package com.motiveschina.hotel.features.plan;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PlanFilter {
    private Long hotelId;
    private String planName;
}

package com.eleven.hotel.application.query;

import com.eleven.core.application.model.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PlanQuery extends PageQuery {
    private String hotelId;
    private String planName;
}

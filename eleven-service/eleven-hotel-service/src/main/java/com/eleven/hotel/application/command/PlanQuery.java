package com.eleven.hotel.application.command;

import com.eleven.core.application.query.PageQuery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PlanQuery extends PageQuery {
    private String planName;
}

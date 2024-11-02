package com.eleven.hotel.application.query;

import com.eleven.core.application.query.PageQuery;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;


@Getter
@Builder
public class PlanQuery extends PageQuery {
    private String planName;
}

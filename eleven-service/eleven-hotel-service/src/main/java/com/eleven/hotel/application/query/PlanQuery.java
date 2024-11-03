package com.eleven.hotel.application.query;

import com.eleven.core.application.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;


@Getter
@Builder
public class PlanQuery extends PageQuery {
    private String planName;
}

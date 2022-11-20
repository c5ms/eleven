package com.eleven.hotel.domain.model.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Builder
@FieldNameConstants
public class PlanDesc {

    @Column(value = "desc")
    private String desc;

}

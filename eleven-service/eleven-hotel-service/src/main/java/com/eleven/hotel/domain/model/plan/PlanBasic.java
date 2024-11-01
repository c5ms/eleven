package com.eleven.hotel.domain.model.plan;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PlanBasic {

    @Column(name = "plan_name")
    private String name;

    @Column(name = "plan_desc")
    private String desc;

}

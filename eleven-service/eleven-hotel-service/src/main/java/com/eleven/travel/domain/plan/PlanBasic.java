package com.eleven.travel.domain.plan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Embeddable
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class PlanBasic implements Serializable {

    @Column(name = "plan_name")
    private String name;

    @Column(name = "plan_desc")
    private String desc;

    public static PlanBasic empty() {
        return new PlanBasic();
    }

}

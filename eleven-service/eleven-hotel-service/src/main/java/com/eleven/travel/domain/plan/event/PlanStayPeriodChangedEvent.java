package com.eleven.travel.domain.plan.event;

import com.eleven.framework.event.DomainEvent;
import com.eleven.travel.domain.plan.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
public class PlanStayPeriodChangedEvent implements DomainEvent {
    private Plan plan;
}

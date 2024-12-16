package com.eleven.domain.plan.event;

import com.eleven.framework.event.DomainEvent;
import com.eleven.domain.plan.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
public class PlanCreatedEvent implements DomainEvent {
    private Plan plan;
}

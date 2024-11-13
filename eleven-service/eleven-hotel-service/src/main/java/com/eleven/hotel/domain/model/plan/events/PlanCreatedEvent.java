package com.eleven.hotel.domain.model.plan.events;

import com.eleven.core.domain.DomainEvent;
import com.eleven.hotel.domain.model.plan.Plan;
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

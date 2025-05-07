package com.motiveschina.hotel.features.plan.event;

import com.eleven.framework.event.DomainEvent;
import com.motiveschina.hotel.features.plan.Plan;
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

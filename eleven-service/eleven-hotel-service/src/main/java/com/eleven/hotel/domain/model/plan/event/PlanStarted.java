package com.eleven.hotel.domain.model.plan.event;

import com.eleven.core.domain.DomainEvent;
import com.eleven.hotel.domain.model.plan.Plan;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PlanStarted implements DomainEvent {
    Plan plan;
}

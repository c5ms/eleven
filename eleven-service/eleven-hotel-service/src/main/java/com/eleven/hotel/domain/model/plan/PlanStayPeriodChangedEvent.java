package com.eleven.hotel.domain.model.plan;

import com.eleven.core.domain.error.DomainEvent;
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

package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.IntegrationEvent;
import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@IntegrationEvent
public class PlanCreatedEvent extends AbstractApplicationEvent {
    private Long hotelId;
    private Long planId;

    public PlanCreatedEvent(Long hotelId, Long planId) {
        this.hotelId = hotelId;
        this.planId = planId;
    }

    public static PlanCreatedEvent of(Long hotelId, Long planId) {
        return new PlanCreatedEvent(hotelId, planId);
    }
}

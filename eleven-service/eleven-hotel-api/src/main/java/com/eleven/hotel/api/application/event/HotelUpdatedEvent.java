package com.eleven.hotel.api.application.event;

import com.eleven.core.application.event.IntegrationEvent;
import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IntegrationEvent(HotelUpdatedEvent.NAME)
public class HotelUpdatedEvent extends AbstractApplicationEvent {
    public static final String NAME = "HotelUpdated";
    private String hotelId;
}

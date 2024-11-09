package com.eleven.booking.api.application.event;

import com.eleven.core.application.event.IntegrationEvent;
import com.eleven.core.application.event.support.AbstractApplicationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@IntegrationEvent
public class DemoEvent extends AbstractApplicationEvent {


}

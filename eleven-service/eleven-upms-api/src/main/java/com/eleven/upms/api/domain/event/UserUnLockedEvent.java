package com.eleven.upms.api.domain.event;

import com.eleven.core.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUnLockedEvent implements DomainEvent {
    String userId;
}

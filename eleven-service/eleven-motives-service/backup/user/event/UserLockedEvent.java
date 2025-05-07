package com.motiveschina.hotel.features.user.event;

import com.eleven.framework.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLockedEvent implements DomainEvent {
    String userId;
}

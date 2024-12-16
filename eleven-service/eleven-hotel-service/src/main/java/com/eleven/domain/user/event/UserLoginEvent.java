package com.eleven.domain.user.event;

import com.eleven.framework.data.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginEvent implements DomainEvent {
    String userId;
}

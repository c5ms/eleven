package com.eleven.upms.api.domain.event;


import com.eleven.framework.data.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenCreatedEvent implements DomainEvent {
    String token;
}

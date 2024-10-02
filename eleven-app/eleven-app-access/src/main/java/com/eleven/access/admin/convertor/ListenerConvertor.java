package com.eleven.access.admin.convertor;

import com.cnetong.access.admin.domain.dto.ListenerDto;
import com.cnetong.access.admin.domain.entity.MessageListenerDefinition;
import com.cnetong.access.core.HealthManager;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListenerConvertor {
    private final MapperFacade mapperFacade;
    private final HealthManager healthManager;

    public ListenerDto toDto(MessageListenerDefinition producerDefinition) {
        var dto = mapperFacade.map(producerDefinition, ListenerDto.class);
        dto.setHealthy(healthManager.getListenerHealthy(producerDefinition.getId()));
        return dto;
    }
}

package com.eleven.access.admin.convertor;

import com.cnetong.access.admin.domain.dto.ProducerDto;
import com.cnetong.access.admin.domain.entity.MessageProducerDefinition;
import com.cnetong.access.core.HealthManager;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerConvertor {
    private final MapperFacade mapperFacade;
    private final HealthManager healthManager;

    public ProducerDto toDto(MessageProducerDefinition messageProducerDefinition) {
        var dto = mapperFacade.map(messageProducerDefinition, ProducerDto.class);
        dto.setHealthy(healthManager.getEndpointHealthy(messageProducerDefinition.getId()));
        return dto;
    }

}

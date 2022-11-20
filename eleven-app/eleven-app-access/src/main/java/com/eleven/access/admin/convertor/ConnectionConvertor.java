package com.eleven.access.admin.convertor;

import com.cnetong.access.admin.domain.dto.ConnectionDto;
import com.cnetong.access.admin.domain.entity.ResourceDefinition;
import com.cnetong.access.core.HealthManager;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectionConvertor {
    private final MapperFacade mapperFacade;
    private final HealthManager healthManager;

    public ConnectionDto toDto(ResourceDefinition resourceDefinition) {
        var dto = mapperFacade.map(resourceDefinition, ConnectionDto.class);
        dto.setHealthy(healthManager.getConnectionHealthy(resourceDefinition.getId()));
        return dto;
    }
}

package com.demcia.eleven.cms.domain.convertor;

import com.demcia.eleven.cms.core.dto.CmsChannelDto;
import com.demcia.eleven.cms.domain.entity.CmsChannel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CmsChannelConverter {
    private final MapperFacade mapperFacade;

    public CmsChannelDto toDto(CmsChannel channel) {
        var dto = mapperFacade.map(channel, CmsChannelDto.class);
        return dto;
    }

}

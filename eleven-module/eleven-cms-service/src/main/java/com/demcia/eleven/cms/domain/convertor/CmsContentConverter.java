package com.demcia.eleven.cms.domain.convertor;

import com.demcia.eleven.cms.core.dto.CmsContentDto;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CmsContentConverter {
    private final MapperFacade mapperFacade;

    public CmsContentDto toDto(CmsContent content) {
        return mapperFacade.map(content, CmsContentDto.class);
    }


}

package com.demcia.eleven.cms.domain.convertor;

import com.demcia.eleven.cms.core.dto.CmsContentSummaryDto;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CmsContentConverter {
    private final MapperFacade mapperFacade;

    public CmsContentSummaryDto toDto(CmsContent content) {
        var dto = mapperFacade.map(content, CmsContentSummaryDto.class);
        dto.setBody(content.getBody().getContent());
        return dto;
    }


}

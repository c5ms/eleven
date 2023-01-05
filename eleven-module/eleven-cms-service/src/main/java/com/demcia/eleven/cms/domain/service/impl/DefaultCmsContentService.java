package com.demcia.eleven.cms.domain.service.impl;

import com.demcia.eleven.cms.core.action.CmsContentCreateAction;
import com.demcia.eleven.cms.core.action.CmsContentUpdateAction;
import com.demcia.eleven.cms.domain.entity.CmsChannelRepository;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import com.demcia.eleven.cms.domain.entity.CmsContentRepository;
import com.demcia.eleven.cms.domain.service.CmsContentService;
import com.demcia.eleven.core.exception.ProcessFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCmsContentService implements CmsContentService {
    private final CmsContentRepository cmsContentRepository;
    private final CmsChannelRepository cmsChannelRepository;

    @Override
    public CmsContent createContent(CmsContentCreateAction action) {
        var channel = cmsChannelRepository.findById(action.getChannel()).orElseThrow(() -> ProcessFailureException.of("栏目不存在"));

        var content = new CmsContent();
        content.setTitle(action.getTitle());
        content.setChannel(channel);

        var body = new CmsContent.CmsContentBody(action.getBody());
        content.setBody(body);
        cmsContentRepository.save(content);
        return content;
    }

    @Override
    public void updateContent(CmsContent content, CmsContentUpdateAction action) {
        cmsContentRepository.save(content);
    }


    @Override
    public Optional<CmsContent> getContent(String id) {
        return cmsContentRepository.findById(id);
    }

    @Override
    public void deleteContent(CmsContent content) {
        cmsContentRepository.delete(content);
    }
}

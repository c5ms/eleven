package com.demcia.eleven.cms.domain.service.impl;

import com.demcia.eleven.cms.core.action.*;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import com.demcia.eleven.cms.domain.entity.CmsContentBody;
import com.demcia.eleven.cms.domain.entity.CmsContentExt;
import com.demcia.eleven.cms.domain.entity.CmsContentTitle;
import com.demcia.eleven.cms.domain.repository.CmsChannelRepository;
import com.demcia.eleven.cms.domain.repository.CmsContentRepository;
import com.demcia.eleven.cms.domain.service.CmsContentService;
import com.demcia.eleven.core.domain.helper.PageableQueryHelper;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCmsContentService implements CmsContentService {
    private final CmsContentRepository cmsContentRepository;
    private final CmsChannelRepository cmsChannelRepository;

    @Override
    public CmsContent createContent(CmsContentCreateAction action) {
        var content = new CmsContent();
        fillContent(action, content);
        cmsContentRepository.save(content);
        return content;
    }

    @Override
    public void updateContent(CmsContent content, CmsContentUpdateAction action) {
        fillContent(action, content);
        cmsContentRepository.save(content);
    }

    @Override
    public CmsContentPublishResult publish(CmsContent content, CmsContentPublishAction action) {
        content.submit();
        cmsContentRepository.save(content);
        return new CmsContentPublishResult().setState(content.getState());
    }

    private void fillContent(CmsContentCreateAction action, CmsContent content) {
        content.setChannel(cmsChannelRepository.findById(action.getChannel()).orElseThrow(() -> ProcessFailureException.of("栏目不存在")));
        if (null == content.getBody()) {
            content.setBody(new CmsContentBody());
        }
        if (null == content.getExt()) {
            content.setExt(new CmsContentExt());
        }
        if (null == content.getTitle()) {
            content.setTitle(new CmsContentTitle());
        }
        BeanUtils.copyProperties(action, content);
        BeanUtils.copyProperties(action, content.getExt());
        BeanUtils.copyProperties(action, content.getBody());
        BeanUtils.copyProperties(action, content.getTitle());
        content.getBody().setText(action.getBody());
    }

    @Override
    public Optional<CmsContent> getContent(String id) {
        return cmsContentRepository.findById(id);
    }

    @Override
    public void deleteContent(CmsContent content) {
        cmsContentRepository.delete(content);
    }

    @Override
    public PaginationResult<CmsContent> queryContent(CmsContentQueryAction queryAction) {
        var spec = Specifications.<CmsContent>and()
                .eq(StringUtils.isNotBlank(queryAction.getChannel()), CmsContent.Fields.channelId, StringUtils.trim(queryAction.getChannel()))
                .build();
        var sort = Sort.by(CmsContent.Fields.id).descending();
        var pageable = PageableQueryHelper.toSpringDataPageable(queryAction, sort);
        var page = cmsContentRepository.findAll(spec, pageable);
        return PageableQueryHelper.toPageResult(page);
    }


}

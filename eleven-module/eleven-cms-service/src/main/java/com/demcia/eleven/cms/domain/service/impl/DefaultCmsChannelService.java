package com.demcia.eleven.cms.domain.service.impl;

import com.demcia.eleven.cms.core.action.CmsChannelCreateAction;
import com.demcia.eleven.cms.core.action.CmsChannelQueryAction;
import com.demcia.eleven.cms.core.action.CmsChannelUpdateAction;
import com.demcia.eleven.cms.domain.entity.CmsChannel;
import com.demcia.eleven.cms.domain.repository.CmsChannelRepository;
import com.demcia.eleven.cms.domain.service.CmsChannelService;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCmsChannelService implements CmsChannelService {
    private final CmsChannelRepository cmsChannelRepository;

    @Override
    public CmsChannel createChannel(CmsChannelCreateAction action) {
        var cmsChannel = new CmsChannel();
        fill(cmsChannel, action);
        cmsChannelRepository.save(cmsChannel);
        return cmsChannel;
    }

    @Override
    public void updateChannel(CmsChannel channel, CmsChannelUpdateAction action) {
        fill(channel, action);
        cmsChannelRepository.save(channel);
    }

    private void fill(CmsChannel cmsChannel, CmsChannelCreateAction action) {
        if (StringUtils.equals(action.getParentId(), cmsChannel.getId())) {
            throw ProcessFailureException.of("父栏目不可以是自己");
        }
        if (StringUtils.isNotBlank(action.getParentId())) {
            cmsChannel.setParent(cmsChannelRepository.findById(action.getParentId()).orElseThrow(() -> ProcessFailureException.of("父级栏目不存在")));
        }
        cmsChannel.setTitle(StringUtils.trim(action.getTitle()));
        cmsChannel.setDescription(StringUtils.trim(action.getDescription()));
    }


    @Override
    public Collection<CmsChannel> listChannels(CmsChannelQueryAction action) {
        var spec = Specifications.<CmsChannel>and()
                .build();
        var sort = Sort.by(CmsChannel.Fields.id).descending();
        return cmsChannelRepository.findAll(spec, sort);
    }

    @Override
    public Optional<CmsChannel> getChannel(String id) {
        return cmsChannelRepository.findById(id);
    }

    @Override
    public void deleteChannel(CmsChannel channel) {
        cmsChannelRepository.delete(channel);
    }


}

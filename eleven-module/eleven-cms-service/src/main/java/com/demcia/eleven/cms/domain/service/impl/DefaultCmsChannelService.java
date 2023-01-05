package com.demcia.eleven.cms.domain.service.impl;

import com.demcia.eleven.cms.core.action.CmsChannelCreateAction;
import com.demcia.eleven.cms.core.action.CmsChannelQueryAction;
import com.demcia.eleven.cms.core.action.CmsChannelUpdateAction;
import com.demcia.eleven.cms.domain.entity.CmsChannel;
import com.demcia.eleven.cms.domain.entity.CmsChannelRepository;
import com.demcia.eleven.cms.domain.service.CmsChannelService;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCmsChannelService implements CmsChannelService {
    private final CmsChannelRepository cmsChannelRepository;

    @Override
    public CmsChannel createChannel(CmsChannelCreateAction action) {
        var cmsChannel = new CmsChannel();
        cmsChannel.setDescription(action.getDescription());
        cmsChannel.setTitle(action.getTitle());
        cmsChannelRepository.save(cmsChannel);
        return cmsChannel;
    }

    @Override
    public void updateChannel(CmsChannel channel, CmsChannelUpdateAction action) {
        if (Objects.nonNull(action.getTitle())) {
            channel.setTitle(StringUtils.trim(action.getTitle()));
        }
        if (Objects.nonNull(action.getDescription())) {
            channel.setDescription(StringUtils.trim(action.getDescription()));
        }
        cmsChannelRepository.save(channel);
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

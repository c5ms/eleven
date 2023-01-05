package com.demcia.eleven.cms.domain.service;

import com.demcia.eleven.cms.core.action.CmsChannelCreateAction;
import com.demcia.eleven.cms.core.action.CmsChannelQueryAction;
import com.demcia.eleven.cms.core.action.CmsChannelUpdateAction;
import com.demcia.eleven.cms.domain.entity.CmsChannel;
import com.demcia.eleven.core.exception.DataNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface CmsChannelService {
    // ======================== write ========================
    CmsChannel createChannel(CmsChannelCreateAction action);

    void updateChannel(CmsChannel channel, CmsChannelUpdateAction action);


    // ======================== read ========================
    Collection<CmsChannel> listChannels(CmsChannelQueryAction action);

    Optional<CmsChannel> getChannel(String id);

    default CmsChannel requireChannel(String id) {
        return this.getChannel(id).orElseThrow(() -> DataNotFoundException.of("栏目不存在"));
    }

    void deleteChannel(CmsChannel channel);
}

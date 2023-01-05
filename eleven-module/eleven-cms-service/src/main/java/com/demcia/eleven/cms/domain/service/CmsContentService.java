package com.demcia.eleven.cms.domain.service;

import com.demcia.eleven.cms.core.action.CmsContentCreateAction;
import com.demcia.eleven.cms.core.action.CmsContentUpdateAction;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import com.demcia.eleven.core.exception.DataNotFoundException;

import java.util.Optional;

public interface CmsContentService {
    // ======================== write ========================
    CmsContent createContent(CmsContentCreateAction action);

    void updateContent(CmsContent channel, CmsContentUpdateAction action);


    // ======================== read ========================

    Optional<CmsContent> getContent(String id);

    default CmsContent requireContent(String id) {
        return this.getContent(id).orElseThrow(() -> DataNotFoundException.of("栏目不存在"));
    }

    void deleteContent(CmsContent channel);
}

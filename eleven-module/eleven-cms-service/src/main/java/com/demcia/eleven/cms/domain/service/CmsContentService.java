package com.demcia.eleven.cms.domain.service;

import com.demcia.eleven.cms.core.action.*;
import com.demcia.eleven.cms.domain.entity.CmsContent;
import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.pageable.PaginationResult;

import java.util.Optional;

public interface CmsContentService {

    // ======================== read ========================

    Optional<CmsContent> getContent(String id);

    default CmsContent requireContent(String id) {
        return this.getContent(id).orElseThrow(() -> DataNotFoundException.of("栏目不存在"));
    }

    void deleteContent(CmsContent channel);

    PaginationResult<CmsContent> queryContent(CmsContentQueryAction queryAction);


    // ======================== write ========================
    CmsContent createContent(CmsContentCreateAction action);

    void updateContent(CmsContent channel, CmsContentUpdateAction action);

    CmsContentPublishResult publish(CmsContent content, CmsContentPublishAction action);

}

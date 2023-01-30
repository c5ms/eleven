package com.demcia.eleven.cms.domain.listener;

import com.demcia.eleven.cms.domain.event.CmsContentPublishEvent;
import com.demcia.eleven.cms.domain.repository.CmsContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CmsContentListener {
    private final CmsContentRepository cmsContentRepository;

    @EventListener(CmsContentPublishEvent.class)
    public void on(CmsContentPublishEvent event) {
        var content = cmsContentRepository.getReferenceById(event.getId());
        log.info("内容被提交，当前状态为：{}", content.getState());
    }

}

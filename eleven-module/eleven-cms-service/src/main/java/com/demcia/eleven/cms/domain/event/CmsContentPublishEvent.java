package com.demcia.eleven.cms.domain.event;

import com.demcia.eleven.cms.domain.entity.CmsContent;
import lombok.Data;

import java.io.Serializable;

@Data
public class CmsContentPublishEvent implements Serializable {

    private String id;

    public CmsContentPublishEvent(CmsContent cmsContent) {
        this.id = cmsContent.getId();
    }
}

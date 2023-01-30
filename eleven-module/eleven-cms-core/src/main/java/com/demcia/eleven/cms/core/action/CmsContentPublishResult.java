package com.demcia.eleven.cms.core.action;

import com.demcia.eleven.cms.core.enums.CmsContentState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class CmsContentPublishResult implements Serializable {
    private CmsContentState state;
}

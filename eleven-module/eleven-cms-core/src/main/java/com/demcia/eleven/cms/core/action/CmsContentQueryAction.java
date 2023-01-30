package com.demcia.eleven.cms.core.action;

import com.demcia.eleven.core.pageable.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CmsContentQueryAction extends Pagination {
    private String channel;

}

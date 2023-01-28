package com.demcia.eleven.cms.core.action;

import com.demcia.eleven.core.pageable.Pagination;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class CmsContentQueryAction extends Pagination {
    private String channel;

}

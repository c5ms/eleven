package com.demcia.eleven.cms.core.action;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CmsContentCreateAction implements Serializable {

    private String title;

    private String body;

    private String channel;

}

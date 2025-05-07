package com.motiveschina.core.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.motiveschina.core.layer.DomainEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainHelper {

    public void publishDomainEvent(DomainEvent event) {
        SpringUtil.publishEvent(event);
    }

}

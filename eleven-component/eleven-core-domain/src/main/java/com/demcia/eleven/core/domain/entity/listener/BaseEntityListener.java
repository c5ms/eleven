package com.demcia.eleven.core.domain.entity.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.core.domain.entity.BaseEntity;
import com.demcia.eleven.core.domain.identity.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;

@Slf4j
@RequiredArgsConstructor
public class BaseEntityListener {

    @PrePersist
    public void touchForCreate(BaseEntity baseEntity) {
        var id = SpringUtil.getBean(IdGenerator.class).nextId(baseEntity.getClass());
        log.debug("创建 JPA 对象 ID : {}#{}", baseEntity.getClass(), id);
        baseEntity.setId(id);
    }


}
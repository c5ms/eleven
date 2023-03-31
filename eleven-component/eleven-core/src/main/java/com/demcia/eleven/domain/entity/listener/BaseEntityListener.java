package com.demcia.eleven.domain.entity.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.domain.entity.BaseEntity;
import com.demcia.eleven.domain.identity.IdGenerator;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.PrePersist;

@Slf4j
@RequiredArgsConstructor
public class BaseEntityListener {

    @Resource
    private  IdGenerator idGenerator;

    @PrePersist
    public void touchForCreate(BaseEntity baseEntity) {
//        var id = SpringUtil.getBean(IdGenerator.class).nextId(baseEntity.getClass());
        var id =idGenerator.nextId(baseEntity.getClass());
        log.debug("创建 JPA 对象 ID : {}#{}", baseEntity.getClass(), id);
        baseEntity.setId(id);
    }


}
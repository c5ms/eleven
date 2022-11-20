package com.demcia.eleven.domain.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityListener {

    @PrePersist
    public void touchForCreate(BaseEntity baseEntity) {
        log.debug("创建 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        if (StringUtils.isBlank(baseEntity.getId())) {
//            log.debug("自动生成 JPA 对象 ID : {}#{}", baseEntity.getClass(), id);
//            baseEntity.setId(id);
        } else {
//            log.debug("更新 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        }
    }


    @PreUpdate
    public void touchForUpdate(BaseEntity baseEntity) {
        log.debug("更新 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
    }

    @PreRemove
    public void touchForDelete(BaseEntity baseEntity) {
        log.debug("删除 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        if (baseEntity.isReserved()) {
            // todo 需要自定义异常
            throw new IllegalStateException("不能删除的数据");
        }

    }

}
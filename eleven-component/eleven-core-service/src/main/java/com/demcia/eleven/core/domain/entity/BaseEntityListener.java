package com.demcia.eleven.core.domain.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Slf4j
@RequiredArgsConstructor
public class BaseEntityListener {

    @PrePersist
    public void touchForCreate(BaseEntity baseEntity) {
//        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许修改数据");
//        }
        log.debug("创建 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        if (null==baseEntity.getId()) {
//            log.debug("自动生成 JPA 对象 ID : {}#{}", baseEntity.getClass(), id);
//            baseEntity.setId(id);
        } else {
//            log.debug("更新 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        }
    }


    @PreUpdate
    public void touchForUpdate(BaseEntity baseEntity) {
//        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许修改数据");
//        }
        log.debug("更新 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
    }

    @PreRemove
    public void touchForDelete(BaseEntity baseEntity) {
//        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许修改数据");
//        }
        log.debug("删除 JPA 对象 ID : {}#{}", baseEntity.getClass(), baseEntity.getId());
        if (baseEntity.isReserved()) {
            // todo 需要自定义异常
            throw new IllegalStateException("不能删除的数据");
        }

    }

}
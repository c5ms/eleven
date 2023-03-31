package com.demcia.eleven.domain.entity.listener;

import com.demcia.eleven.domain.entity.BaseAuditableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

@Slf4j
@RequiredArgsConstructor
public class BaseAuditableEntityListener {

    @PrePersist
    public void touchForCreate(BaseAuditableEntity entity) {
        //        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许创建数据");
//        }
        log.debug("创建 JPA 对象 ID : {}#{}", entity.getClass(), entity.getId());
    }

    @PreUpdate
    public void touchForUpdate(BaseAuditableEntity entity) {
//        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许修改数据");
//        }
        log.debug("更新 JPA 对象 ID : {}#{}", entity.getClass(), entity.getId());
    }

    @PreRemove
    public void touchForDelete(BaseAuditableEntity entity) {
//        if(1==1){
//            throw  new PermissionDeadException("演示环境，不允许修改数据");
//        }
        log.debug("删除 JPA 对象 ID : {}#{}", entity.getClass(), entity.getId());
        if (entity.isReserved()) {
            // todo 需要自定义异常
            throw new IllegalStateException("不能删除的数据");
        }

    }

}
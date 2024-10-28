package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationContext {

    public void publishEvent(ApplicationEvent applicationEvent) {
        SpringUtil.publishEvent(applicationEvent);
    }

//    public boolean isReadable(Object resource) {
//        return SpringUtil.getBean(ResourceSecurityManager.class).isReadable(resource);
//    }

//    public boolean isWritable(Object resource) {
//        return SpringUtil.getBean(ResourceSecurityManager.class).isWritable(resource);
//    }

    public boolean mustReadable(Object resource) throws NoReadPermissionException {
        return SpringUtil.getBean(ResourceSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWritePermissionException {
        return SpringUtil.getBean(ResourceSecurityManager.class).mustWritable(resource);
    }

    public NoWritePermissionException noWritePermission() {
        return NoWritePermissionException.instance();
    }

    public NoReadPermissionException noReadPermission() {
        return NoReadPermissionException.instance();
    }

    public NoResourceException noResource() {
        return NoResourceException.instance();
    }

    public void throwNoWritePermission() {
        throw noWritePermission();
    }

    public void throwNoReadPermission() {
        throw noReadPermission();
    }

    public void throwNoResource() {
        throw noResource();
    }

}

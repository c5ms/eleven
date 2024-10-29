package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationHelper {

    public String getServiceName() {
        return SpringUtil.getApplicationName();
    }

    public void publishEvent(ApplicationEvent event) {
        SpringUtil.publishEvent(event);
    }

    public boolean mustReadable(Object resource) throws NoReadPermissionException {
        return SpringUtil.getBean(ResourceSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWritePermissionException {
        return SpringUtil.getBean(ResourceSecurityManager.class).mustWritable(resource);
    }

    public NoWritePermissionException createNoWritePermissionEx() {
        return NoWritePermissionException.instance();
    }

    public NoReadPermissionException createNoReadPermissionEx() {
        return NoReadPermissionException.instance();
    }

    public NoResourceException createNoResourceEx() {
        return NoResourceException.instance();
    }

    public void throwNoWritePermissionEx() {
        throw createNoWritePermissionEx();
    }

    public void throwNoReadPermissionEx() {
        throw createNoReadPermissionEx();
    }

    public void throwNoResourceEx() {
        throw createNoResourceEx();
    }

}

package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.security.NoReadPermissionException;
import com.eleven.core.application.security.NoWritePermissionException;
import com.eleven.core.application.security.ResourceSecurityManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationContext {

    public void publishEvent(ApplicationEvent applicationEvent) {
        SpringUtil.publishEvent(applicationEvent);
    }

    public boolean isReadable(Object resource) {
        return SpringUtil.getBean(ResourceSecurityManager.class).isReadable(resource);
    }

    public boolean isWritable(Object resource) {
        return SpringUtil.getBean(ResourceSecurityManager.class).isWritable(resource);
    }

    public void mustReadable(Object resource) {
        SpringUtil.getBean(ResourceSecurityManager.class).mustReadable(resource);
    }

    public void mustWritable(Object resource) {
        SpringUtil.getBean(ResourceSecurityManager.class).mustWritable(resource);
    }

    public NoWritePermissionException noWritePermission() {
        return NoWritePermissionException.instance();
    }

    public NoReadPermissionException noReadPermission() {
        return NoReadPermissionException.instance();
    }

}

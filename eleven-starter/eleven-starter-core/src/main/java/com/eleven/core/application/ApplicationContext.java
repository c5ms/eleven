package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.security.ApplicationSecurityManager;
import com.eleven.core.application.security.NoAccessPermissionException;
import com.eleven.core.application.security.NoWritePermissionException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationContext {

    public void publishEvent(ApplicationEvent applicationEvent) {
        SpringUtil.publishEvent(applicationEvent);
    }

    public boolean isAccessible(Object resource)  {
        return SpringUtil.getBean(ApplicationSecurityManager.class).isReadable(resource);
    }

    public boolean isWritable(Object resource) {
        return SpringUtil.getBean(ApplicationSecurityManager.class).isWritable(resource);
    }

    public static NoAccessPermissionException noAccessPermission () {
        return NoAccessPermissionException.because("the user has no permission or the resource is not exist");
    }

    public static NoWritePermissionException noWritePermission () {
        return NoWritePermissionException.because("the user has no permission or the resource is not exist");
    }



}

package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.security.NoPrincipalException;
import com.eleven.core.application.security.NoReadAuthorityException;
import com.eleven.core.application.security.NoWriteAuthorityException;
import com.eleven.core.application.security.ObjectSecurityManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationHelper {

    public String getServiceName() {
        return SpringUtil.getApplicationName();
    }

    public void publishEvent(ApplicationEvent event) {
        SpringUtil.publishEvent(event);
    }

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustWritable(resource);
    }

    public NoWriteAuthorityException noWritePermissionException() {
        return NoWriteAuthorityException.instance();
    }

    public NoReadAuthorityException noReadPermissionException() {
        return NoReadAuthorityException.instance();
    }

    public NoPrincipalException noPrincipalException() {
        return NoPrincipalException.instance();
    }

}

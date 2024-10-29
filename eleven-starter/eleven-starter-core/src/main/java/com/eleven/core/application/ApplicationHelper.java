package com.eleven.core.application;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.command.NoCommandAcceptorException;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.secure.NoReadAuthorityException;
import com.eleven.core.application.secure.NoWriteAuthorityException;
import com.eleven.core.application.secure.DomainSecurityManager;
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
        return SpringUtil.getBean(DomainSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(DomainSecurityManager.class).mustWritable(resource);
    }

    public NoWriteAuthorityException noWritePermissionException() {
        return NoWriteAuthorityException.instance();
    }

    public NoReadAuthorityException noReadPermissionException() {
        return NoReadAuthorityException.instance();
    }

    public NoCommandAcceptorException noCommandAcceptorException() {
        return NoCommandAcceptorException.instance();
    }

}

package com.motiveschina.erp.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.framework.authorize.NoPrincipalException;
import com.eleven.framework.authorize.NoReadAuthorityException;
import com.eleven.framework.authorize.NoWriteAuthorityException;
import com.eleven.framework.authorize.ObjectSecurityManager;
import com.eleven.framework.domain.DomainError;
import com.motiveschina.erp.support.layer.DomainEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomainSupport {

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustWritable(resource);
    }

    public void must(Boolean check, DomainError domainError) throws NoWriteAuthorityException {
        if (!check) {
            domainError.throwException();
        }
    }

    public NoPrincipalException noPrincipalException() {
        return new NoPrincipalException();
    }

    public void publishDomainEvent(DomainEvent event) {
        SpringUtil.publishEvent(event);
    }
}

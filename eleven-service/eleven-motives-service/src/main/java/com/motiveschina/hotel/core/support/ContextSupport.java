package com.motiveschina.hotel.core.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.framework.authorize.NoReadAuthorityException;
import com.eleven.framework.authorize.NoWriteAuthorityException;
import com.eleven.framework.authorize.ObjectSecurityManager;
import com.eleven.framework.domain.NoDomainEntityException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContextSupport {

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustWritable(resource);
    }

    public NoDomainEntityException noPrincipalException() {
        return NoDomainEntityException.instance();
    }

}

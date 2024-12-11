package com.eleven.core.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.core.application.authorize.NoReadAuthorityException;
import com.eleven.core.application.authorize.NoWriteAuthorityException;
import com.eleven.core.application.authorize.ObjectSecurityManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContextSupport {

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustWritable(resource);
    }

    public NoPrincipalException noPrincipalException() {
        return new NoPrincipalException();
    }

}

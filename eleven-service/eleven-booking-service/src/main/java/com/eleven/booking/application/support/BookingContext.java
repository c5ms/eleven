package com.eleven.booking.application.support;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.application.event.ApplicationEvent;
import com.eleven.core.application.authorize.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingContext {

    public void publishEvent(ApplicationEvent event) {
        SpringUtil.publishEvent(event);
    }

    public boolean mustReadable(Object resource) throws NoReadAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustReadable(resource);
    }

    public boolean mustWritable(Object resource) throws NoWriteAuthorityException {
        return SpringUtil.getBean(ObjectSecurityManager.class).mustWritable(resource);
    }

    public NoPrincipalException noPrincipalException() {
        return new NoPrincipalException();
    }

    public NoEntityException noEntityException() {
        return new NoEntityException();
    }

}

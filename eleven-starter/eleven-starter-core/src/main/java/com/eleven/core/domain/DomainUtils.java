package com.eleven.core.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.event.DomainEvent;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.function.Supplier;

@UtilityClass
public class DomainUtils {

   private static IdentityGenerator identityGenerator;

    public static void must(boolean check, Supplier<RuntimeException> e) {
        if (BooleanUtils.isFalse(check)) {
            throw e.get();
        }
    }

    public static void must(boolean check, DomainError error) throws DomainException {
        must(check, error::toException);
    }

    public static void mustNot(boolean check, Supplier<RuntimeException> e) {
        if (BooleanUtils.isTrue(check)) {
            throw e.get();
        }
    }

    public static void mustNot(boolean check, DomainError error) throws DomainException {
        mustNot(check, error::toException);
    }

    public static String nextId() {
        return identityGenerator.next();
    }

    public void publishEvent(DomainEvent domainEvent) {
        SpringUtil.publishEvent(domainEvent);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration(proxyBeanMethods = false)
    static class DomainHelperAutoconfigure {
        @Autowired
        public DomainHelperAutoconfigure(IdentityGenerator identityGenerator) {
            DomainUtils.identityGenerator = identityGenerator;
        }
    }

}

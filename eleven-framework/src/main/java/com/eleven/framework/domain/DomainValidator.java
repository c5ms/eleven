package com.eleven.framework.domain;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.BooleanUtils;

import java.util.function.Supplier;

@UtilityClass
public class DomainValidator {

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

}

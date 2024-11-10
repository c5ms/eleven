package com.eleven.core.domain;


public interface DomainError {

    String getError();

    String getMessage();

    default DomainException toException() {
        return DomainException.of(this);
    }

    default void throwException() {
        throw DomainException.of(this);
    }

}

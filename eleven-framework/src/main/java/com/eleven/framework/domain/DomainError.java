package com.eleven.framework.domain;


public interface DomainError {

    String getError();

    String getMessage();

    default DomainException toException() {
        return DomainException.of(this);
    }

}

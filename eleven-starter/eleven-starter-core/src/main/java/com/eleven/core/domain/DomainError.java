package com.eleven.core.domain;


public interface DomainError {

    /**
     * 获取编码
     *
     * @return 编码
     */
    String getError();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getMessage();

    default DomainException toException() {
        return DomainException.of(this);
    }

    default void throwException() {
        throw DomainException.of(this);
    }

}

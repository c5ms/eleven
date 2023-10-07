package com.eleven.core.domain;

/**
 * 一个可以变为标识的类
 */
public interface Identifiable {
    /**
     * 得到该对象的标识
     *
     * @return 标识属性
     */
    String identity();
}

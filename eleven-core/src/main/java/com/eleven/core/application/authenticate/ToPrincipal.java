package com.eleven.core.application.authenticate;

/**
 * 标记类可以转换为一个主体对象
 */
public interface ToPrincipal {
    /**
     * 转换为主体对象
     *
     * @return 主题对象
     */
    Principal toPrincipal();
}

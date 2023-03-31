package com.demcia.eleven.upms.domain;

/**
 * 用户授权器
 */
public interface UserGrantor {

    /**
     * 这个权限是否由此授权器管理
     *
     * @param authority 权限
     * @return true 表示他负责这个权限的管理
     */
    boolean support(Authority authority);

    /**
     * 执行用户授权
     * @param user 要授权的用户
     * @param authority 要授予的权限
     */
    void grant(User user, Authority authority);

}

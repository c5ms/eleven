package com.eleven.core.security;

import org.springframework.security.access.AccessDeniedException;

/**
 * 主体认证器
 */
public interface PrincipalAuthenticatorProvider {

    /**
     * 判断一个主体是否受到支持
     *
     * @param principal 要判断的主体
     * @return true 表示支持此主体对象
     */
    boolean support(Principal principal);

    /**
     * 认证主体对象
     *
     * @param principal 要认证的主体
     * @return 认证后的概要信息
     * @throws AccessDeniedException -
     */
    Subject authenticate(Principal principal) throws AccessDeniedException;

}

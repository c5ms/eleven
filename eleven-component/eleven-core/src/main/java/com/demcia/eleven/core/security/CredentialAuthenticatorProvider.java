package com.demcia.eleven.core.security;

import org.springframework.security.access.AccessDeniedException;

/**
 * 访问主体认证器，根据访问凭证，提供访问主体的认证
 */
public interface CredentialAuthenticatorProvider {

    /**
     * 给出当前认证器是否支持
     *
     * @param credentialInstance 要认证的凭证
     * @return true 表示可以支持此凭证
     */
    boolean support(CredentialInstance credentialInstance);

    /**
     * 验证凭证身份，验证后返回凭证所代表的的身份主体，如果无法认证主体，则抛出异常
     *
     * @param credentialInstance 用户凭证
     * @return 认证通过的身份主体
     * @throws AccessDeniedException 当凭证无法认证的时候
     */
    Principal authenticate(CredentialInstance credentialInstance) throws AccessDeniedException;

}

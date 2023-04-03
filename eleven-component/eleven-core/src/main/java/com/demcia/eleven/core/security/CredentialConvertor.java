package com.demcia.eleven.core.security;

public interface CredentialConvertor {

    boolean support(Credential credential);

    /**
     * 依据客户端和请求的信息，创建对应的凭证
     *
     * @param credential 请求信息
     * @return 请求凭证
     */
    CredentialInstance createCredential(Credential credential);

}
package com.demcia.eleven.core.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CredentialAuthenticator {
    private final List<CredentialConvertor> convertors;
    private final Collection<CredentialAuthenticatorProvider> credentialAuthenticatorProviders;


    /**
     * 认证一个凭证
     *
     * @param credentialInformation 凭证信息
     * @return 认证得到的凭证代表的主体
     */
    public Principal authenticate(final Credential credentialInformation) {
        // 转换
        var credential = convertors.stream()
                .filter(credentialConvertor -> credentialConvertor.support(credentialInformation))
                .findFirst()
                .map(credentialConvertor -> credentialConvertor.createCredential(credentialInformation))
                .orElseThrow(() -> new AccessDeniedException("验证失败，请检查输入信息"));

        // 认证
        var provider = credentialAuthenticatorProviders.stream()
                .filter(authenticator -> authenticator.support(credential))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("不支持的认证类型，" + credential.getClass().getName()));
        return provider.authenticate(credential);
    }

}
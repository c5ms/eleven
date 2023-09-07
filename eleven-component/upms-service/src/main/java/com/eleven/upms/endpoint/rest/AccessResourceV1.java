package com.eleven.upms.endpoint.rest;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.eleven.core.rest.annonation.AsRestApi;
import com.eleven.core.security.Principal;
import com.eleven.core.security.SecurityService;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenDetail;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.AccessTokenCreateAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Tag(name = "令牌")
@RequestMapping("/access_tokens")
@AsRestApi
@RequiredArgsConstructor
public class AccessResourceV1 {

    private final UserService userService;
    private final SecurityService securityService;
    private final AccessTokenService accessTokenService;

    @Operation(summary = "创建令牌")
    @PostMapping
    public Token createToken(@RequestBody @Validated AccessTokenCreateAction request, HttpServletRequest servletRequest) {
        var identity = request.getIdentity();
        var credential = request.getCredential();
        var principal = login(new Principal(identity), credential);

        if (principal.isEmpty()) {
            throw UpmsConstants.ERROR_UNSUPPORTED_IDENTITY.exception();
        }

        var clientIp = JakartaServletUtil.getClientIP(servletRequest);
        var detail = new TokenDetail().setClientIp(clientIp);
        var token = securityService.createToken(principal.get(), detail);
        var accessToken = new AccessToken(token);

        accessTokenService.saveToken(accessToken);
        return token;
    }

    public Optional<Principal> login(Principal principal, String credential) {

        // 系统内置用户
        if (StringUtils.equals(UpmsConstants.PRINCIPAL_TYPE_USER, principal.getType())) {
            var userOptional = userService.loginUser(principal.getName(), credential);
            if (userOptional.isEmpty()) {
                throw UpmsConstants.ERROR_LOGIN_PASSWORD.exception();
            } else {
                return userOptional.map(User::toPrincipal);
            }
        }


        return Optional.empty();
    }


}

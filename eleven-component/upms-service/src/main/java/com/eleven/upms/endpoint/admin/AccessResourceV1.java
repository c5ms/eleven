package com.eleven.upms.endpoint.admin;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.eleven.core.rest.annonation.AsAdminApi;
import com.eleven.core.security.*;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "令牌")
@RequestMapping("/access_tokens")
@AsAdminApi
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

    @Operation(summary = "读取令牌内容")
    @GetMapping("/{token}/subject")
    public Optional<Subject> getSubjectFor(@PathVariable("token") String value) {
        if (SecurityContext.isAnonymous()) {
            throw new AccessDeniedException("您无权访问");
        }

        if (SecurityContext.isNotAdmin() && !SecurityContext.getToken().getValue().equals(value)) {
            throw new AccessDeniedException("您无权访问");
        }

        return securityService.verifyToken(value)
                .map(Token::getPrincipal)
                .map(securityService::readSubject);
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

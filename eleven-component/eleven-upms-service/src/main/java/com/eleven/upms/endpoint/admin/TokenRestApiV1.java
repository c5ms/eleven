package com.eleven.upms.endpoint.admin;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.eleven.core.security.*;
import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.AccessTokenCreateAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "token")
@RequestMapping("/tokens")
@AsAdminApi
@RequiredArgsConstructor
public class TokenRestApiV1 {

    private final UserService userService;
    private final SecurityService securityService;
    private final AccessTokenService accessTokenService;

    @Operation(summary = "create token")
    @PostMapping
    public Token createToken(@RequestBody @Validated AccessTokenCreateAction request, HttpServletRequest servletRequest) {
        var identity = request.getIdentity();
        var credential = request.getCredential();
        var principal = login(new Principal(identity), credential);
        var clientIp = JakartaServletUtil.getClientIP(servletRequest);
        var detail = new TokenDetail().setClientIp(clientIp);
        var token = securityService.createToken(principal, detail);
        accessTokenService.saveToken(token);
        return token;
    }

    @Operation(summary = "get token subject")
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

    private Principal login(Principal principal, String credential) {
        if (principal.getType().equals(UpmsConstants.PRINCIPAL_TYPE_USER)) {
            return userService.loginUser(principal.getName(), credential);
        }
        throw UpmsConstants.ERROR_UNSUPPORTED_IDENTITY.exception();
    }


}

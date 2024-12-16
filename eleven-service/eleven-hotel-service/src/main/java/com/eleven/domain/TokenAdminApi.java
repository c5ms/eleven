package com.eleven.domain;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.eleven.framework.interfaces.annonation.AsAdminApi;
import com.eleven.upms.api.application.command.AccessTokenCreateCommand;
import com.eleven.upms.application.service.TokenService;
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
public class TokenAdminApi {

    private final TokenService tokenService;

    @Operation(summary = "create token")
    @PostMapping
    public Token createToken(@RequestBody @Validated AccessTokenCreateCommand request, HttpServletRequest servletRequest) {
        var identity = request.getIdentity();
        var credential = request.getCredential();
        var clientIp = JakartaServletUtil.getClientIP(servletRequest);
        var detail = new TokenDetail().setClientIp(clientIp);
        var principal = new Principal(identity);
        return tokenService.login(principal, credential, detail);
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
        return tokenService.getSubject(value)
            .filter(subject1 -> subject1.getPrincipal().equals(SecurityContext.requirePrincipal()));
    }


}

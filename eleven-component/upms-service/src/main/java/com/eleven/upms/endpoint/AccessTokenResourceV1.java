package com.eleven.upms.endpoint;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.eleven.core.rest.annonation.RestResource;
import com.eleven.core.security.Principal;
import com.eleven.core.security.Token;
import com.eleven.core.security.TokenDetail;
import com.eleven.core.time.TimeContext;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.core.UpmsError;
import com.eleven.upms.domain.AccessToken;
import com.eleven.upms.domain.AccessTokenService;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.AccessTokenCreateAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "令牌")
@RequestMapping("/access_tokens")
@RestResource
@RequiredArgsConstructor
public class AccessTokenResourceV1 {

    private final UserService userService;
    private final AccessTokenService accessTokenService;

    @Operation(summary = "创建令牌")
    @PostMapping
    public Token createToken(@RequestBody @Validated AccessTokenCreateAction request, HttpServletRequest servletRequest) {
        var identity = request.getIdentity();
        var principal = new Principal(identity);
        var credential = request.getCredential();

        // 系统内置用户
        if (StringUtils.equals(UpmsConstants.PRINCIPAL_TYPE_LOCAL_USER, principal.getType())) {
            var userOptional = userService.loginUser(principal.getName(), credential);
            if (userOptional.isEmpty()) {
                throw UpmsError.LOGIN_PASSWORD_ERROR.exception();
            }
        }
        // 其他身份不支持
        else {
            throw UpmsError.UNSUPPORTED_IDENTITY.exception();
        }

        var clientIp = JakartaServletUtil.getClientIP(servletRequest);
        var detail = new TokenDetail().setClientIp(clientIp);
        var value = IdUtil.fastSimpleUUID();
        // 用户的令牌可用 10 天,但是目前还没有超时的处理
        var token = new Token()
                .setIssuer(SpringUtil.getApplicationName())
                .setExpireAt(TimeContext.localDateTime().plusDays(10))
                .setPrincipal(principal)
                .setDetail(detail)
                .setValue(value);
        var accessToken = new AccessToken(token);
        accessTokenService.saveToken(accessToken);
        return token;
    }

    @Operation(summary = "读取令牌")
    @GetMapping("/{token}")
    public Optional<Token> readToken(@PathVariable("token") String token) {
        return accessTokenService.readToken(token).map(AccessToken::toToken);
    }


}

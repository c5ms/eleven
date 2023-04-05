package com.demcia.eleven.upms.application.endpoint;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.demcia.eleven.core.application.rest.annonation.RestResource;
import com.demcia.eleven.core.security.Principal;
import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.core.security.TokenDetail;
import com.demcia.eleven.core.time.TimeContext;
import com.demcia.eleven.upms.application.request.AccessTokenCreateRequest;
import com.demcia.eleven.upms.domain.AccessToken;
import com.demcia.eleven.upms.domain.AccessTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Tag(name = "令牌")
@RequestMapping("/access_tokens")
@RestResource
@RequiredArgsConstructor
public class AccessTokenResourceV1 {

    private final AccessTokenService accessTokenService;

    @Operation(summary = "创建令牌")
    @PostMapping
    public Token createToken(@RequestBody @Validated AccessTokenCreateRequest request, HttpServletRequest httpServletRequest) {
        var clientIp = fixIp(JakartaServletUtil.getClientIP(httpServletRequest));
        var principal = new Principal("user", "1642444151711207424");
        var detail = new TokenDetail().setClientIp(clientIp);
        var value = IdUtil.fastSimpleUUID();
        // 用户的令牌可用 10 天,但是目前还没有超时的处理
        var token = new Token()
                .setIssuer(SpringUtil.getApplicationName())
                .setExpireAt(TimeContext.localDateTime().plus(10, ChronoUnit.DAYS))
                .setPrincipal(principal)
                .setDetail(detail)
                .setValue(value);
        return accessTokenService.createToken(token).toToken();
    }

    @Operation(summary = "读取令牌")
    @GetMapping("/{token}")
    public Optional<Token> readToken(@PathVariable("token") String token) {
        return accessTokenService.readToken(token).map(AccessToken::toToken);
    }

    private String fixIp(String clientIp) {
        if (clientIp.equals("::1") || clientIp.equals("0:0:0:0:0:0:0:1") || clientIp.equals("127.0.0.1")) {
            clientIp = "127.0.0.1";
        }

        if (clientIp.startsWith("::ffff:")) {
            clientIp = clientIp.substring(7);
        }

        return clientIp;
    }


}

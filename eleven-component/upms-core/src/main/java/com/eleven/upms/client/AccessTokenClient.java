package com.eleven.upms.client;

import com.eleven.core.security.Token;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.dto.AccessTokenCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(contextId = "AccessTokenClient",
        name = UpmsConstants.SERVICE_NAME,
        url = UpmsConstants.SERVICE_URL,
        path = UpmsConstants.NAMESPACE_ACCESS_TOKENS)
public interface AccessTokenClient {

    @Operation(summary = "创建令牌")
    @PostMapping
    Token createToken(@RequestBody @Validated AccessTokenCreateRequest request);

    @Operation(summary = "读取令牌")
    @GetMapping("/{token}")
    Optional<Token> readToken(@PathVariable("token") String token);
}

package com.demcia.eleven.upms.app.api;

import com.demcia.eleven.upms.app.request.AccessTokenCreateRequest;
import com.demcia.eleven.upms.app.dto.AccessTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "AccessTokenApi", value = "eleven-upms")
public interface AccessTokenApi {
    @Operation(summary = "创建令牌")
    @PostMapping
    AccessTokenDto createToken(@RequestBody @Validated AccessTokenCreateRequest request, HttpServletRequest httpServletRequest);
}

package com.demcia.eleven.upms.api;

import com.demcia.eleven.upms.dto.AccessTokenDto;
import com.demcia.eleven.upms.request.AccessTokenCreateRequest;
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

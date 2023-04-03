package com.demcia.eleven.upms.application.api;

import com.demcia.eleven.upms.application.dto.AccessTokenDto;
import com.demcia.eleven.upms.application.request.AccessTokenCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "eleven-upms")
public interface AccessTokenApi {
    @Operation(summary = "创建令牌")
    @PostMapping
    AccessTokenDto createToken(@RequestBody @Validated AccessTokenCreateRequest request, HttpServletRequest httpServletRequest);
}

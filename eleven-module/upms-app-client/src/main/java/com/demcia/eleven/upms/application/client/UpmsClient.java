package com.demcia.eleven.upms.application.client;

import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.core.security.Token;
import com.demcia.eleven.upms.application.core.dto.UserAuthorityDto;
import com.demcia.eleven.upms.application.core.dto.UserDto;
import com.demcia.eleven.upms.application.core.request.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@FeignClient(value = "eleven-upms")
public interface UpmsClient {

    @Operation(summary = "用户查询")
    @GetMapping("/users")
    QueryResult<UserDto> queryUser(@SpringQueryMap UserQueryRequest request);

    @Operation(summary = "用户读取")
    @GetMapping("/users/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @Operation(summary = "用户创建")
    @PostMapping("/users")
    UserDto createUser(@RequestBody @Validated UserCreateRequest request);

    @Operation(summary = "用户更新")
    @PostMapping("/users/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request);

    @Operation(summary = "用户删除")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") String id);

    @Operation(summary = "用户权限列表")
    @GetMapping("/users/{id}/authorities")
    Set<UserAuthorityDto> getUserAuthorities(@PathVariable("id") String id);

    @Operation(summary = "用户授权")
    @PostMapping("/users/{id}/authorities")
    void grantUser(@PathVariable("id") String id, @RequestBody @Validated UserGrantRequest request);

    @Operation(summary = "创建令牌")
    @PostMapping("/access_tokens")
    Token createToken(@RequestBody @Validated AccessTokenCreateRequest request);

    @Operation(summary = "读取令牌")
    @GetMapping("/access_tokens/{token}")
    Optional<Token> readToken(@PathVariable("token") String token);
}
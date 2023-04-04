package com.demcia.eleven.upms.api;

import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.upms.dto.UserAuthorityDto;
import com.demcia.eleven.upms.dto.UserDto;
import com.demcia.eleven.upms.request.UserCreateRequest;
import com.demcia.eleven.upms.request.UserGrantRequest;
import com.demcia.eleven.upms.request.UserQueryRequest;
import com.demcia.eleven.upms.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@FeignClient(contextId = "UserApi", value = "eleven-upms", path = "/users")
public interface UserApi {

    @Operation(summary = "用户查询")
    @GetMapping
    QueryResult<UserDto> queryUser(@SpringQueryMap UserQueryRequest request);

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @Operation(summary = "用户创建")
    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateRequest request);

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request);

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id);

    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    Set<UserAuthorityDto> getUserAuthorities(@PathVariable("id") String id);

    @Operation(summary = "用户授权")
    @PostMapping("/{id}/authorities")
    void grantUser(@PathVariable("id") String id, @RequestBody @Validated UserGrantRequest request);
}
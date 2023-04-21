package com.eleven.upms.app.client;

import com.eleven.core.query.QueryResult;
import com.eleven.upms.app.constants.UpmsConstants;
import com.eleven.upms.app.dto.UserAuthorityDto;
import com.eleven.upms.app.dto.UserDto;
import com.eleven.upms.app.request.UserCreateRequest;
import com.eleven.upms.app.request.UserGrantRequest;
import com.eleven.upms.app.request.UserQueryRequest;
import com.eleven.upms.app.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@FeignClient(contextId = "UserClient", name = UpmsConstants.SERVICE_NAME, url = UpmsConstants.SERVICE_URL, path = UpmsConstants.NAMESPACE_USERS)
public interface UserClient {

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
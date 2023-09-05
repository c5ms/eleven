package com.eleven.upms.client;

import com.eleven.core.model.PaginationResult;
import com.eleven.upms.core.UpmsConstants;
import com.eleven.upms.model.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@FeignClient(contextId = "UserClient",
        name = UpmsConstants.SERVICE_NAME,
        url = UpmsConstants.SERVICE_URL,
        path = UpmsConstants.NAMESPACE_USERS)
public interface UserClient {

    @Operation(summary = "用户查询")
    @GetMapping
    PaginationResult<UserDto> queryUserPage(@SpringQueryMap UserFilter request);

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    Optional<UserDetail> getUser(@PathVariable("id") String id);

    @Operation(summary = "用户创建")
    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateAction action);

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction action);

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id);

    @Operation(summary = "用户锁定")
    @PostMapping("/{id}/_lock")
    UserDto lockUser(@PathVariable("id") String id);

    @Operation(summary = "用户解锁")
    @PostMapping("/{id}/_unlock")
    UserDto unlockUser(@PathVariable("id") String id);

    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    List<PowerDto> listUserAuthorities(@PathVariable("id") String id);

}

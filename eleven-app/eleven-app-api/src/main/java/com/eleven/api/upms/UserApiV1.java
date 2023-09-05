package com.eleven.api.upms;

import com.eleven.core.model.PaginationResult;
import com.eleven.core.rest.annonation.RestResource;
import com.eleven.upms.client.UserClient;
import com.eleven.upms.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserApiV1 {
    private final UserClient userClient;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        return userClient.createUser(action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userClient.deleteUser(id);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction updateAction) {
        return userClient.updateUser(id, updateAction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDetail> getUser(@PathVariable("id") String id) {
        return userClient.getUser(id);
    }

    @GetMapping
    @Operation(summary = "用户查询")
    public PaginationResult<UserDto> queryUser(@ParameterObject UserFilter request) {
        return userClient.queryUserPage(request);
    }

}

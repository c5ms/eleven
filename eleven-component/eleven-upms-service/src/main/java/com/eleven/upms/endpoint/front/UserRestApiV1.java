package com.eleven.upms.endpoint.front;

import com.eleven.core.domain.PaginationResult;
import com.eleven.core.web.annonation.AsRestApi;
import com.eleven.upms.action.UserCreateAction;
import com.eleven.upms.action.UserUpdateAction;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.dto.*;
import com.eleven.upms.query.UserQuery;
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
@AsRestApi
@RequiredArgsConstructor
public class UserRestApiV1 {

    private final UserService userService;

    @Operation(summary = "用户查询")
    @GetMapping
    public PaginationResult<UserDto> queryUserPage(@ParameterObject UserQuery filter) {
        return userService.queryUserPage(filter);
    }

    @Operation(summary = "用户详情")
    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        return userService.createUser(action);
    }

    @Operation(summary = "用户更新")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction action) {
        return userService.updateUser(id, action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }


}

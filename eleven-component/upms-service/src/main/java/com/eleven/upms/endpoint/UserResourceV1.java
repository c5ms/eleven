package com.eleven.upms.endpoint;

import com.eleven.core.model.PaginationResult;
import com.eleven.core.rest.annonation.RestResource;
import com.eleven.core.rest.exception.NotFoundException;
import com.eleven.upms.application.UserConverter;
import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 {

    private final UserService userService;
    private final UserConverter userConverter;

    @Operation(summary = "用户查询")
    @GetMapping
    public PaginationResult<UserDto> queryUser(@ParameterObject UserQuery query) {
        return userService.queryUserPage(query).map(userConverter::toDto);
    }

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id).orElseThrow(NotFoundException::new);
    }

    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction action) {
        var user = getUser(id);
        userService.updateUser(user, action);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        var user = getUser(id);
        userService.deleteUser(user);
    }


    @Operation(summary = "用户权限表")
    @GetMapping("/{id}/authorities")
    public Set<UserAuthorityDto> getUserAuthorities(@PathVariable("id") String id) {
        var user = getUser(id);
        return userService.getAuthorities(user).stream().map(userConverter::toDto).collect(Collectors.toSet());
    }


    @Operation(summary = "用户锁定")
    @PostMapping("/{id}/_lock")
    public UserDto lockUser(@PathVariable("id") String id) {
        var user = getUser(id);
        userService.lockUser(user);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户解锁")
    @DeleteMapping("/{id}/_lock")
    public UserDto unlockUser(@PathVariable("id") String id) {
        var user = getUser(id);
        userService.unlockUser(user);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户授权")
    @PostMapping("/{id}/authorities")
    public void grantUser(@PathVariable("id") String id, @RequestBody @Validated UserGrantAction action) {
        var user = getUser(id);
        userService.grant(user, action);
    }
}

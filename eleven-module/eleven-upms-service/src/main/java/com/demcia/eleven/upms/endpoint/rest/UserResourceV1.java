package com.demcia.eleven.upms.endpoint.rest;

import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.domain.Authority;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.action.UserGrantAction;
import com.demcia.eleven.upms.domain.action.UserQueryAction;
import com.demcia.eleven.upms.domain.action.UserUpdateAction;
import com.demcia.eleven.upms.domain.UserDto;
import com.demcia.eleven.upms.domain.UserConverter;
import com.demcia.eleven.upms.domain.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public PaginationResult<UserDto> queryUser(@ParameterObject UserQueryAction queryAction) {
        return userService.queryUser(queryAction).map(userConverter::toDto);
    }

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String id) {
        var user = userService.requireUser(id);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction updateAction) {
        var user = userService.requireUser(id);
        userService.updateUser(user, updateAction);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.getUser(id).ifPresent(userService::deleteUser);
    }

    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    public Set<Authority> getUserAuthorities(@PathVariable("id") String id) {
        var user = userService.requireUser(id);
        return userService.authoritiesOfUser(user).getAuthorities();
    }

    @Operation(summary = "用户授权")
    @PostMapping("/{id}/authorities")
    public void grantUser(@PathVariable("id") String id ,@RequestBody @Validated UserGrantAction action) {
        var user = userService.requireUser(id);
         userService.grant(user,action);
    }


}

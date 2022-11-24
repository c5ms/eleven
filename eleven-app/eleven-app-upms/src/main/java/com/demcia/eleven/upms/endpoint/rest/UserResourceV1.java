package com.demcia.eleven.upms.endpoint.rest;

import com.demcia.eleven.core.pageable.PageResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQuery;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.convertor.UserConvertor;
import com.demcia.eleven.upms.core.dto.UserDto;
import com.demcia.eleven.upms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "users")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 {

    private final UserService userService;
    private final UserConvertor userConvertor;

    @Operation(summary = "创建用户")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return userConvertor.toDto(user);
    }

    @Operation(summary = "查询用户")
    @GetMapping
    public PageResult<UserDto> queryUser(@ParameterObject UserQuery queryAction) {
        return userService.queryUser(queryAction).map(userConvertor::toDto);
    }

    @Operation(summary = "读取用户")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        var user = userService.requireUser(id);
        return userConvertor.toDto(user);
    }


    @Operation(summary = "更新用户")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateAction updateAction) {
        var user = userService.requireUser(id);
        userService.updateUser(user, updateAction);
        return userConvertor.toDto(user);
    }

}

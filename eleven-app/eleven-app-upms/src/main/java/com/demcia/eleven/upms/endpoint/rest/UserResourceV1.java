package com.demcia.eleven.upms.endpoint.rest;

import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import com.demcia.eleven.upms.domain.convertor.UserConvertor;
import com.demcia.eleven.upms.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 {

    private final UserService userService;
    private final UserConvertor userConvertor;

    @Operation(summary = "查询用户")
    @GetMapping
    public PaginationResult<UserDto> queryUser(@ParameterObject UserQueryAction queryAction,
                                               @ParameterObject Pagination pagination) {
        return userService.queryUser(queryAction, pagination).map(userConvertor::toDto);
    }

    @Operation(summary = "读取用户")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") String id) {
        var user = userService.requireUser(id);
        return userConvertor.toDto(user);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return userConvertor.toDto(user);
    }

    @Operation(summary = "更新用户")
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction updateAction) {
        var user = userService.requireUser(id);
        userService.updateUser(user, updateAction);
        return userConvertor.toDto(user);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id) {
        var user = userService.requireUser(id);
        userService.deleteUser(user);
    }

}

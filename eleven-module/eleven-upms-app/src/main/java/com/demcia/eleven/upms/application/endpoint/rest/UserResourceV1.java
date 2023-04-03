package com.demcia.eleven.upms.application.endpoint.rest;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.query.Pagination;
import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.application.api.UserApi;
import com.demcia.eleven.upms.domain.Authority;
import com.demcia.eleven.upms.domain.User;
import com.demcia.eleven.upms.domain.UserService;
import com.demcia.eleven.upms.application.dto.UserAuthorityDto;
import com.demcia.eleven.upms.application.dto.UserDto;
import com.demcia.eleven.upms.application.request.UserCreateRequest;
import com.demcia.eleven.upms.application.request.UserGrantRequest;
import com.demcia.eleven.upms.application.request.UserQueryRequest;
import com.demcia.eleven.upms.application.request.UserUpdateRequest;
import com.demcia.eleven.upms.application.convertor.UserConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 implements UserApi {

    private final UserService userService;
    private final UserConverter userConverter;

    @Override
    @Operation(summary = "用户查询")
    @GetMapping
    public QueryResult<UserDto> queryUser(@ParameterObject UserQueryRequest request) {
        var filter = userConverter.toFilter(request);
        var pagination = Pagination.of(request.getPage(), request.getSize());
        return userService.queryUser(filter, pagination).map(userConverter::toDto);
    }
    @Override
    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userService.getUser(id).map(userConverter::toDto);
    }
    @Override
    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateRequest request) {
        var action = userConverter.toAction(request);
        var user = userService.createUser(action);
        return userConverter.toDto(user);
    }
    @Override
    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request) {
        var action = userConverter.toAction(request);
        var user = requireUser(id);
        userService.updateUser(user, action);
        return userConverter.toDto(user);
    }
    @Override
    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.getUser(id).ifPresent(userService::deleteUser);
    }

    @Override
    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    public Set<UserAuthorityDto> getUserAuthorities(@PathVariable("id") String id) {
        var user = requireUser(id);
        return userService.getAuthorities(user)
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toSet());
    }
    @Override
    @Operation(summary = "用户授权")
    @PostMapping("/{id}/authorities")
    public void grantUser(@PathVariable("id") String id, @RequestBody @Validated UserGrantRequest request) {
        var authority = Authority.of(request.getType(), request.getName());
        var user = requireUser(id);
        userService.grant(user, authority);
    }

    public User requireUser(String id) {
        return userService.getUser(id).orElseThrow(() -> DataNotFoundException.of("用户不存在"));
    }

}

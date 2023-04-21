package com.eleven.upms.app.endpoint;

import com.eleven.core.query.Pagination;
import com.eleven.core.query.QueryResult;
import com.eleven.core.app.rest.annonation.RestResource;
import com.eleven.core.app.rest.exception.NotFoundException;
import com.eleven.upms.app.dto.UserAuthorityDto;
import com.eleven.upms.app.dto.UserDto;
import com.eleven.upms.app.request.UserCreateRequest;
import com.eleven.upms.app.request.UserGrantRequest;
import com.eleven.upms.app.request.UserQueryRequest;
import com.eleven.upms.app.request.UserUpdateRequest;
import com.eleven.upms.app.converter.UserConverter;
import com.eleven.upms.domain.Authority;
import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserService;
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
public class UserResourceV1 {

    private final UserService userService;
    private final UserConverter userConverter;

    @Operation(summary = "用户查询")
    @GetMapping
    public QueryResult<UserDto> queryUser(@ParameterObject UserQueryRequest request) {
        var filter = userConverter.toFilter(request);
        var pagination = Pagination.of(request.getPage(), request.getSize());
        return userService.queryUser(filter, pagination).map(userConverter::toDto);
    }

    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userService.getUser(id).map(userConverter::toDto);
    }

    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateRequest request) {
        var action = userConverter.toAction(request);
        var user = userService.createUser(action);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request) {
        var action = userConverter.toAction(request);
        var user = requireUser(id);
        userService.updateUser(user, action);
        return userConverter.toDto(user);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.getUser(id).ifPresent(userService::deleteUser);
    }

    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    public Set<UserAuthorityDto> getUserAuthorities(@PathVariable("id") String id) {
        var user = requireUser(id);
        return userService.getAuthorities(user)
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Operation(summary = "用户授权")
    @PostMapping("/{id}/authorities")
    public void grantUser(@PathVariable("id") String id, @RequestBody @Validated UserGrantRequest request) {
        var authority = Authority.of(request.getType(), request.getName());
        var user = requireUser(id);
        userService.grant(user, authority);
    }

    public User requireUser(String id) {
        return userService.getUser(id).orElseThrow(NotFoundException::new);
    }

}

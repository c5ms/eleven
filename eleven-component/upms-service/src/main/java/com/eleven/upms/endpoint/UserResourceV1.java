package com.eleven.upms.endpoint;

import com.eleven.core.model.PaginationResult;
import com.eleven.core.rest.annonation.RestResource;
import com.eleven.core.rest.exception.NotFoundException;
import com.eleven.upms.client.UserClient;
import com.eleven.upms.domain.AuthorityConvertor;
import com.eleven.upms.domain.User;
import com.eleven.upms.domain.UserConvertor;
import com.eleven.upms.domain.UserService;
import com.eleven.upms.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 implements UserClient {

    private final UserService userService;

    private final AuthorityConvertor authorityConvertor;
    private final UserConvertor userConvertor;

    @Override
    @Operation(summary = "用户查询")
    @GetMapping
    public PaginationResult<UserDto> queryUserPage(@ParameterObject UserFilter filter) {
        return userService.queryUserPage(filter).map(userConvertor::toDto);
    }

    @Override
    @Operation(summary = "用户读取")
    @GetMapping("/{id}")
    public Optional<UserDetail> getUser(@PathVariable("id") String id) {
        return userService.getUser(id).map(userConvertor::toDetail);
    }

    @Override
    @Operation(summary = "用户创建")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = userService.createUser(action);
        return userConvertor.toDto(user);
    }

    @Override
    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction action) {
        var user = requireUser(id);
        userService.updateUser(user, action);
        return userConvertor.toDto(user);
    }

    @Override
    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.deleteUser(user);
    }

    @Override
    @Operation(summary = "用户锁定")
    @PostMapping("/{id}/_lock")
    public UserDto lockUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.lockUser(user);
        return userConvertor.toDto(user);
    }

    @Override
    @Operation(summary = "用户解锁")
    @PostMapping("/{id}/_unlock")
    public UserDto unlockUser(@PathVariable("id") String id) {
        var user = requireUser(id);
        userService.unlockUser(user);
        return userConvertor.toDto(user);
    }

    @Override
    @Operation(summary = "用户权限列表")
    @GetMapping("/{id}/authorities")
    public List<PowerDto> listUserAuthorities(@PathVariable("id") String id) {
        var user = requireUser(id);
        return userService.listPower(user)
                .stream()
                .map(authorityConvertor::toDto)
                .toList();
    }

    private User requireUser(String id) {
        return userService.getUser(id).orElseThrow(NotFoundException::new);
    }
}

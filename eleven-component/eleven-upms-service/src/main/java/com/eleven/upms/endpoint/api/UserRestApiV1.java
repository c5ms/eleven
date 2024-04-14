package com.eleven.upms.endpoint.api;

import com.eleven.core.domain.PaginationResult;
import com.eleven.core.web.annonation.AsFrontApi;
import com.eleven.upms.domain.UserService;
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
@Tag(name = "user")
@RequestMapping("/users")
@AsFrontApi
@RequiredArgsConstructor
public class UserRestApiV1 {

    private final UserService userService;

    @Operation(summary = "query users")
    @GetMapping
    public PaginationResult<UserDto> queryUserPage(@ParameterObject UserQuery filter) {
        return userService.queryUserPage(filter);
    }

    @Operation(summary = "get user")
    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }


    @Operation(summary = "get user summary")
    @GetMapping("/{id}/summary")
    public Optional<UserSummary> getUserSummary(@PathVariable("id") String id) {
        return userService.getUserSummary(id);
    }

    @Operation(summary = "create user")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        return userService.createUser(action);
    }

    @Operation(summary = "update user")
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody @Validated UserUpdateAction action) {
        return userService.updateUser(id, action);
    }

    @Operation(summary = "delete user")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}

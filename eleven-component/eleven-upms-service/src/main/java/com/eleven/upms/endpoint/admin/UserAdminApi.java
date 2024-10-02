package com.eleven.upms.endpoint.admin;

import com.eleven.core.domain.PaginationResult;
import com.eleven.core.exception.NoRequiredDataException;
import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.upms.application.service.UserService;
import com.eleven.upms.core.command.UserCreateCommand;
import com.eleven.upms.core.command.UserQueryCommand;
import com.eleven.upms.core.command.UserUpdateCommand;
import com.eleven.upms.core.model.UserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "user")
@RequestMapping("/users")
@AsAdminApi
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @Operation(summary = "query users")
    @GetMapping
    public PaginationResult<UserDetail> queryUserPage(@ParameterObject UserQueryCommand filter) {
        return userService.queryUser(filter);
    }

    @Operation(summary = "get user")
    @GetMapping("/{id}")
    public UserDetail getUser(@PathVariable("id") String id) {
        return userService.getUser(id).orElseThrow(NoRequiredDataException::new);
    }

    @Operation(summary = "create user")
    @PostMapping
    public UserDetail createUser(@RequestBody @Validated UserCreateCommand action) {
        return userService.createUser(action);
    }

    @Operation(summary = "update user")
    @PutMapping("/{id}")
    public UserDetail updateUser(@PathVariable("id") String id, @RequestBody @Validated UserUpdateCommand action) {
        return userService.updateUser(id, action);
    }

    @Operation(summary = "delete user")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}

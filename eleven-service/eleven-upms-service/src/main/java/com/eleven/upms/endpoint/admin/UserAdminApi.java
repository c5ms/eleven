package com.eleven.upms.endpoint.admin;

import com.eleven.core.model.PageResult;
import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.upms.application.service.UserService;
import com.eleven.upms.api.application.command.UserCreateCommand;
import com.eleven.upms.api.application.command.UserQueryCommand;
import com.eleven.upms.api.application.command.UserStatusChangeCommand;
import com.eleven.upms.api.application.model.UserDetail;
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
@AsAdminApi
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @Operation(summary = "query users")
    @GetMapping
    public PageResult<UserDetail> queryUserPage(@ParameterObject UserQueryCommand filter) {
        return userService.queryUser(filter);
    }

    @Operation(summary = "get user")
    @GetMapping("/{id}")
    public Optional<UserDetail> getUser(@PathVariable("id") String id)  {
        return userService.getUser(id);
    }

    @Operation(summary = "create user")
    @PostMapping
    public UserDetail createUser(@RequestBody @Validated UserCreateCommand command) {
        return userService.createUser(command);
    }

    @Operation(summary = "update user status")
    @PutMapping("/{id}/status")
    public UserDetail updateUser(@PathVariable("id") String id, @RequestBody @Validated UserStatusChangeCommand command) {
        return userService.updateUserStatus(id, command);
    }

    @Operation(summary = "delete user")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}

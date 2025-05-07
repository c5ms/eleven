package com.motiveschina.hotel.features.user;

import java.util.Optional;
import cn.hutool.db.PageResult;
import com.eleven.framework.web.annonation.AsRestApi;
import com.motiveschina.hotel.features.user.command.UserCreateCommand;
import com.motiveschina.hotel.features.user.command.UserQueryCommand;
import com.motiveschina.hotel.features.user.command.UserStatusChangeCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "user")
@RequestMapping("/users")
@AsRestApi
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
    public Optional<UserDetail> getUser(@PathVariable("id") String id) {
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

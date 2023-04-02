package com.demcia.eleven.api.upms;

import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.client.UserResourceClient;
import com.demcia.eleven.upms.domain.UserDto;
import com.demcia.eleven.upms.domain.request.UserCreateRequest;
import com.demcia.eleven.upms.domain.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserApiV1 {
    private final UserResourceClient userResourceClient;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateRequest action) {
        return userResourceClient.createUser(action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userResourceClient.deleteUser(id);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction) {
        return userResourceClient.updateUser(id, updateAction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userResourceClient.getUser(id);
    }


}

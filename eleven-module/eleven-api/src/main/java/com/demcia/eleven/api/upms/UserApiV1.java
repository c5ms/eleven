package com.demcia.eleven.api.upms;

import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.api.UserApi;
import com.demcia.eleven.upms.dto.UserDto;
import com.demcia.eleven.upms.request.UserCreateRequest;
import com.demcia.eleven.upms.request.UserQueryRequest;
import com.demcia.eleven.upms.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserApiV1 {
    private final UserApi userApi;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateRequest action) {
        return userApi.createUser(action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userApi.deleteUser(id);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction) {
        return userApi.updateUser(id, updateAction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userApi.getUser(id);
    }


    @GetMapping
    @Operation(summary = "用户查询")
    public QueryResult<UserDto> queryUser(@ParameterObject UserQueryRequest request) {
        return userApi.queryUser(request);
    }
}

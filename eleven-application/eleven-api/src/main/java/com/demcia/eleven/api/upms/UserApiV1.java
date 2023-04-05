package com.demcia.eleven.api.upms;

import com.demcia.eleven.core.application.rest.annonation.RestResource;
import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.upms.application.client.UpmsClient;
import com.demcia.eleven.upms.application.core.dto.UserDto;
import com.demcia.eleven.upms.application.core.request.UserCreateRequest;
import com.demcia.eleven.upms.application.core.request.UserQueryRequest;
import com.demcia.eleven.upms.application.core.request.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserApiV1 {
    private final UpmsClient upmsClient;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateRequest action) {
        return upmsClient.createUser(action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        upmsClient.deleteUser(id);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction) {
        return upmsClient.updateUser(id, updateAction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return upmsClient.getUser(id);
    }

    @GetMapping
    @Operation(summary = "用户查询")
    public QueryResult<UserDto> queryUser(@ParameterObject UserQueryRequest request) {
        return upmsClient.queryUser(request);
    }

}

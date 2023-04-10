package com.eleven.api.upms;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.generate.support.RaindropGenerator;
import com.eleven.core.query.QueryResult;
import com.eleven.core.service.rest.annonation.RestResource;
import com.eleven.upms.api.dto.UserDto;
import com.eleven.upms.api.request.UserCreateRequest;
import com.eleven.upms.api.request.UserQueryRequest;
import com.eleven.upms.api.request.UserUpdateRequest;
import com.eleven.upms.client.UserClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Tag(name = "用户")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserApiV1 {
    private final UserClient userClient;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateRequest action) {
        return userClient.createUser(action);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userClient.deleteUser(id);
    }

    @Operation(summary = "用户更新")
    @PostMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction) {
        return userClient.updateUser(id, updateAction);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userClient.getUser(id);
    }

    @GetMapping
    @Operation(summary = "用户查询")
    public QueryResult<UserDto> queryUser(@ParameterObject UserQueryRequest request) {
        return userClient.queryUser(request);
    }

}

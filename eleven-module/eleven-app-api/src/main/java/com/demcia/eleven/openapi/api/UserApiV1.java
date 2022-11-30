package com.demcia.eleven.openapi.api;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.PermissionDeadException;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.demcia.eleven.core.exception.UnauthorizedException;
import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.upms.client.UserResourceClient;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
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
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        return userResourceClient.createUser(action);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userResourceClient.getUser(id);
    }


    @GetMapping
    @Operation(summary = "用户查询")
    public PaginationResult<UserDto> queryUser(@ParameterObject UserQueryAction queryAction) {
//        throw DataNotFoundException.of("数据不存在");
        throw  new IllegalStateException();
//        return userResourceClient.queryUser(queryAction);
    }

}

package com.demcia.eleven.openapi.api;

import com.demcia.eleven.upms.client.UserResourceClient;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.dto.UserDto;
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
@RestController
@RequiredArgsConstructor
public class UserApiV1 {
    private final UserResourceClient userResourceClient;

    @PostMapping
    @Operation(summary = "用户创建")
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        System.out.println("处理了大量应用层面的逻辑，组合了多个远程服务和本地服务");
        return userResourceClient.createUser(action);
    }


    @GetMapping("/{id}")
    @Operation(summary = "用户读取")
    public Optional<UserDto> getUser(@PathVariable("id") String id) {
        return userResourceClient.getUser(id);
    }

}

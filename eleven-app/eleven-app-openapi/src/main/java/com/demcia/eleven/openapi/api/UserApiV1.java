package com.demcia.eleven.openapi.api;

import com.demcia.eleven.upms.client.RemoteUserResource;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "users")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserApiV1 {
    private final RemoteUserResource remoteUserResource;

    @GetMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        System.out.println("处理了大量应用层面的逻辑，组合了多个远程服务和本地服务");
        // TODO 这里会出现分布式事务的问题？！
        return remoteUserResource.createUser(action);
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") Long id) {
        System.out.println("处理了大量应用层面的逻辑，组合了多个远程服务和本地服务");
        // TODO 这里会出现分布式事务的问题？！
        return remoteUserResource.getUser(id);
    }

}

package com.demcia.eleven.app.upms.controller;

import com.demcia.eleven.app.upms.request.UserCreateRequest;
import com.demcia.eleven.upms.client.RemoteUserResource;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "users")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final RemoteUserResource remoteUserResource;
    private final MapperFacade mapperFacade;

    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateRequest request) {
        var action = mapperFacade.map(request, UserCreateAction.class);
        System.out.println("处理了大量应用层面的逻辑，组合了多个远程服务和本地服务");
        // TODO 这里会出现分布式事务的问题？！
        return remoteUserResource.createUser(action);
    }

}

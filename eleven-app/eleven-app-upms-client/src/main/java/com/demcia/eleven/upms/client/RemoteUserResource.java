package com.demcia.eleven.upms.client;

import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(contextId = "RemoteUserResource",
        name = "eleven-upms",
        url = "${service.eleven-domain-upms-service-url:9000:}",
        path = "/users")
public interface RemoteUserResource {

    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateAction action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") Long id);

}

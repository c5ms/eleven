package com.demcia.eleven.upms.client;

import com.demcia.eleven.upms.constants.UpmsConstants;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(contextId = "RemoteUserResource",
        name = UpmsConstants.SERVICE_NAME,
        url = UpmsConstants.SERVICE_URL,
        path = UpmsConstants.NAMESPACE_USERS)
public interface RemoteUserResource {

    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateAction action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") Long id);

}

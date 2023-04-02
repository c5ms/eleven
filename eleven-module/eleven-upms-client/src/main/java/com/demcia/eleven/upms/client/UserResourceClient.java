package com.demcia.eleven.upms.client;

import com.demcia.eleven.upms.domain.UserDto;
import com.demcia.eleven.upms.domain.request.UserCreateRequest;
import com.demcia.eleven.upms.domain.request.UserUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "eleven-upms",
        url = "${service.eleven-upms.url:}",
        path = "${service.eleven-upms.path:/users}"
)
public interface UserResourceClient {

    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateRequest action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @PostMapping("/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id);
}

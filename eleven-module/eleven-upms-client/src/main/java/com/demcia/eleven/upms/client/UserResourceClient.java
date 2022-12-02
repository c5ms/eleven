package com.demcia.eleven.upms.client;

import com.demcia.eleven.core.pageable.PaginationResult;
import com.demcia.eleven.upms.core.action.UserCreateAction;
import com.demcia.eleven.upms.core.action.UserQueryAction;
import com.demcia.eleven.upms.core.action.UserUpdateAction;
import com.demcia.eleven.upms.core.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
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
    UserDto createUser(@RequestBody @Validated UserCreateAction action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @GetMapping
    PaginationResult<UserDto> queryUser(@SpringQueryMap UserQueryAction queryAction);

    @PostMapping("/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateAction updateAction);

    @DeleteMapping("/{id}")
     void deleteUser(@PathVariable("id") String id);
}

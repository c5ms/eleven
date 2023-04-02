package com.demcia.eleven.upms.api;

import com.demcia.eleven.core.query.QueryResult;
import com.demcia.eleven.upms.dto.UserDto;
import com.demcia.eleven.upms.request.UserCreateRequest;
import com.demcia.eleven.upms.request.UserQueryRequest;
import com.demcia.eleven.upms.request.UserUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "eleven-upms",
        url = "${service.eleven-upms.url:http://127.0.0.1:9000}",
        path = "${service.eleven-upms.path:/users}"
)
public interface UserApi {

    @GetMapping
    QueryResult<UserDto> queryUser(@SpringQueryMap UserQueryRequest request);

    @PostMapping
    UserDto createUser(@RequestBody @Validated UserCreateRequest action);

    @GetMapping("/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @PostMapping("/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction);

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id);
}
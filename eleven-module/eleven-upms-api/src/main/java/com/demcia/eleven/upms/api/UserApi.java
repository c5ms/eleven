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

@FeignClient(value = "eleven-upms")
public interface UserApi {

    @GetMapping("/users")
    QueryResult<UserDto> queryUser(@SpringQueryMap UserQueryRequest request);

    @PostMapping("/users")
    UserDto createUser(@RequestBody @Validated UserCreateRequest action);

    @GetMapping("/users/{id}")
    Optional<UserDto> getUser(@PathVariable("id") String id);

    @PostMapping("/users/{id}")
    UserDto updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest updateAction);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") String id);
}
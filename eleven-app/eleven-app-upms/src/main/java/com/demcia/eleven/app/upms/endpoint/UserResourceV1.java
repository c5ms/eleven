package com.demcia.eleven.app.upms.endpoint;

import com.demcia.eleven.core.rest.annonation.RestResource;
import com.demcia.eleven.domain.upms.action.UserCreateAction;
import com.demcia.eleven.domain.upms.convertor.UserConvertor;
import com.demcia.eleven.domain.upms.dto.UserDto;
import com.demcia.eleven.domain.upms.entity.User;
import com.demcia.eleven.domain.upms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "users")
@RequestMapping("/users")
@RestResource
@RequiredArgsConstructor
public class UserResourceV1 {

    private final UserService userService;
    private final UserConvertor userConvertor;

    @Operation(summary = "创建用户")
    @PostMapping
    public UserDto createUser(@RequestBody @Validated UserCreateAction action) {
        var user = new User();
        user.setUsername(action.getUsername());
        userService.saveUser(user);
        return userConvertor.toDto(user);
    }

    @Operation(summary = "读取用户")
    @GetMapping("/{id}")
    public Optional<UserDto> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id).map(userConvertor::toDto);
    }

}

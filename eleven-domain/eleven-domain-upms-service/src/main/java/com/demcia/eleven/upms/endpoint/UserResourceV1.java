package com.demcia.eleven.upms.endpoint;

import com.demcia.eleven.endpoint.rest.RestResource;
import com.demcia.eleven.upms.constants.UpmsConstants;
import com.demcia.eleven.upms.convertor.UserConvertor;
import com.demcia.eleven.upms.domain.User;
import com.demcia.eleven.upms.domain.action.UserCreateAction;
import com.demcia.eleven.upms.domain.dto.UserDto;
import com.demcia.eleven.upms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
